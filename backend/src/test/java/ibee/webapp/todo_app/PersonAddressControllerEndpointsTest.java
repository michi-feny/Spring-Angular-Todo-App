package ibee.webapp.todo_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import ibee.webapp.todo_app.core.entity.Address;
import ibee.webapp.todo_app.core.entity.Country;
import ibee.webapp.todo_app.core.service.AddressServiceImpl;
import ibee.webapp.todo_app.core.service.CountryServiceImpl;
import ibee.webapp.todo_app.dto.AddressDto;
import ibee.webapp.todo_app.dto.CountryDto;
import ibee.webapp.todo_app.features.person.dto.PersonData;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonAddressDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.features.person.service.PersonDtoService;
import ibee.webapp.todo_app.mapper.AddressMapper;
import ibee.webapp.todo_app.mapper.CountryMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional // Resets the DB after each individual @Test method
@WithMockUser(username = "admin", roles = {"USER"}) // Applied to all tests
public class PersonAddressControllerEndpointsTest extends BaseIntegrationTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonDtoService personDtoService; 

    @Autowired
    private CountryServiceImpl countryService;

    @Autowired
    private AddressServiceImpl addressService;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private CountryMapper countryMapper;

    private static final String BASE_URL = "/api/v1/person-addresses"; //[cite: 1]

    // Shared state for tests, initialized before each test runs
    private Long seededPersonId;
    private Long seededAddressId;
    private AddressDto seededAddressDto;
    private String selfUrl;
    private String detailsUrl;
    private CountryDto countryDto;

    @BeforeEach
    public void setupRealPersonAndAddress() throws Exception {
        // 1. Create Real Person
        // 1. Utilize helper method to create a baseline Person
        PersonData savedPerson = createTestPerson(
            "Test", "User", 
            123401011990L, LocalDate.of(1990, 1, 1));
        seededPersonId = savedPerson.id();
        assertNotNull(seededPersonId);

        // 2. Fetch an ACTUAL existing country from the database
        // (Assuming you have a method like findAll() or can fetch by code like "DE")
        Country realCountry = countryService.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No countries seeded in test database! Make sure reference data is loaded."));

        this.countryDto = countryMapper.toDto(realCountry);

        // 3. Utilize helper method to create a baseline Address
        Address savedAddress = createTestAddress(
            "Setup St", "1", 
            "Setup City", "100", realCountry);
        assertNotNull(savedAddress.getId());
        seededAddressId = savedAddress.getId();
        seededAddressDto = addressMapper.toDto(savedAddress);

        // 4. Build payload using correct composite ID constructor order: (addressId, personId)
        PersonAddressDto dtoPayload = 
            createPersonAddressPayload(seededAddressId, seededPersonId, 
                seededAddressDto, true);
        
        //  5. Perform POST & Extract HATEOAS Links for the tests to use[cite: 2, 3]
        MvcResult result = mockMvc.perform(post(BASE_URL)
                .with(user("admin").roles("USER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoPayload)))
                .andExpect(status().isCreated())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        selfUrl = JsonPath.read(response, "$.data._links.self.href");
        detailsUrl = selfUrl + "/details"; // Matches the standard /details route
    }

    // ===================================================================================
    // Helper Methods for Building Test Objects & Payloads
    // ===================================================================================

    /**
     * Helper to cleanly instantiate and persist test persons.
     */
    private PersonData createTestPerson(String firstName, String lastName, Long ssn, LocalDate birthDate) {
        PersonData payload = new PersonData(null, ssn, firstName, lastName, birthDate);
        PersonData saved = personDtoService.create(payload);
        assertNotNull(saved.id());
        return saved;
    }

    /**
     * Helper to cleanly instantiate and persist test addresses.
     */
    private Address createTestAddress(String street, String houseNumber, String city, String zipCode, Country country) {
        Address address = new Address(null, street, houseNumber, city, zipCode, country);
        Address saved = addressService.create(address);
        assertNotNull(saved.getId());
        return saved;
    }

    /**
     * Helper to build PersonAddressDto payloads with the exact correct composite ID layout.
     */
    private PersonAddressDto createPersonAddressPayload(Long addressId, Long personId, AddressDto addressDto, boolean mainAddress) {
        // Ensures proper argument order (addressId, personId) matching PersonAddressDtoId record definition
        PersonAddressDtoId compositeId = new PersonAddressDtoId(addressId, personId);
        return new PersonAddressDto(compositeId, addressDto, mainAddress);
    }

    // ===================================================================================
    // 1. Standard CRUD Endpoints
    // ===================================================================================

    @Test
    public void testGetAll_shouldReturnCollection() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data._embedded.personAddressDtoList").exists())
                .andExpect(jsonPath("$.data._embedded.personAddressDtoList.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(1)));
    }

    @Test
    public void testGetById_shouldReturnSingleEntity() throws Exception {
        mockMvc.perform(get(selfUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.address.street").value("Setup St"))
                .andExpect(jsonPath("$.data._links.self.href").value(selfUrl));
    }

    @Test
    public void testCreate_shouldReturnCreatedEntity() throws Exception {
        // Leverage helper methods for concise object setup
        PersonData newPerson = createTestPerson("New", "Guy", 9876543210L, LocalDate.of(1995, 5, 5));
        Address savedNewAddress = createTestAddress("New St", "99", "New City", "999", countryMapper.toEntity(countryDto));
        AddressDto savedNewAddressDto = addressMapper.toDto(savedNewAddress);

        PersonAddressDto newPayload = createPersonAddressPayload(savedNewAddress.getId(), newPerson.id(), savedNewAddressDto, false);
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPayload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.address.street").value("New St"))
                .andExpect(jsonPath("$.data.mainAddress").value(false));
    }

    @Test
    public void testUpdate_shouldModifyExistingEntity() throws Exception {
        AddressDto updatedAddress = 
            new AddressDto(null, "Updated St", 
            "2", "Updated City", 
            "200", countryDto.id());
        
        PersonAddressDto updatePayload = 
            createPersonAddressPayload(seededAddressId, seededPersonId, 
                updatedAddress, false);
        
        mockMvc.perform(put(selfUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePayload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.address.street").value("Updated St"))
                .andExpect(jsonPath("$.data.mainAddress").value(false));
    }

    @Test
    public void testDelete_shouldRemoveEntity() throws Exception {
        // Delete the entity
        mockMvc.perform(delete(selfUrl))
                .andExpect(status().isOk());

        // Verify it no longer exists
        mockMvc.perform(get(selfUrl))
                .andExpect(status().isNotFound());
    }

    // ===================================================================================
    // 2. Person-Specific Endpoints[cite: 1]
    // ===================================================================================

    @Test
    public void testGetAllByPersonId_shouldReturnAssociatedAddresses() throws Exception {
        mockMvc.perform(get(BASE_URL + "/person/" + seededPersonId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data._embedded.personAddressDtoList[0].address.street").value("Setup St"));
    }

    @Test
    public void testGetReferenceIdsByPersonId_shouldReturnLightweightIds() throws Exception {
        mockMvc.perform(get(BASE_URL + "/person/" + seededPersonId + "/ids"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].personId").value(seededPersonId));
    }

    @Test
    public void testGetWithDetailsById_shouldReturnExtendedView() throws Exception {
        mockMvc.perform(get(detailsUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.address.city").value("Setup City"));
    }

   // ===================================================================================
    // 3. Advanced Scenario Endpoints
    // ===================================================================================

    /**
     * Verifies that adding a new main address automatically toggles any existing
     * main addresses for the same person to false, ensuring that a person maintains
     * at most one active main address at any given time.
     */
    @Test
    public void testUpdate_shouldToggleOldMainAddressesWhenNewMainIsAdded() throws Exception {
        // 1. Verify initial state: seeded address is the main address
        // WHY: We must establish a known baseline. 
        //  Before adding any new addresses, 
        //  our setup fixture (selfUrl) must explicitly be 
        // the primary (mainAddress = true).
    mockMvc.perform(get(selfUrl))
            .andExpect(status().isOk())
            .andExpect(
                    jsonPath("$.data.mainAddress")
                    .value(true));

    // 2. Create a second address with mainAddress = true
    Address addr2 = 
        createTestAddress("Street 2", "2", 
        "City 2", "200", countryMapper.toEntity(countryDto));
    PersonAddressDto payload2 = 
        createPersonAddressPayload(addr2.getId(), seededPersonId, 
        addressMapper.toDto(addr2), true);

    MvcResult res2 = mockMvc.perform(post(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(payload2)))
            .andExpect(status().isCreated())
            // WHY CHECK HERE: 
            //      Immediately verify that the newly created resource 
            //      successfully accepted and saved `mainAddress = true` 
            //      upon insertion.
            .andExpect(jsonPath("$.data.mainAddress").value(true))
            .andReturn();
            
    // THE "URL THING" (HATEOAS Dynamic Link Extraction):
    // Because our backend uses Spring HATEOAS, resources are assigned dynamic, 
    // hypermedia-driven URIs containing composite keys 
    // (e.g., /api/v1/person-addresses/addressId-personId). 
    // Since we don't hardcode these IDs in our tests, 
    // we use `JsonPath.read(...)` to parse the 
    // JSON response string of the `201 Created` result, 
    // grabbing the exact `_links.self.href` 
    // generated by the server. 
    // This gives us a valid pointer to query `url2` later.
    String url2 = JsonPath.read(res2.getResponse().getContentAsString(), "$.data._links.self.href");

    // 3. IMMEDIATELY confirm that adding the new main address 
    // flipped the original one to false
    // WHY: Immediate assertion prevents masking bugs. 
    // If adding addr2 failed to demote the original address, 
    // we catch it right here rather than waiting until the end of the test.
    mockMvc.perform(get(selfUrl))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.mainAddress").value(false));

    // 4. Create a third address with mainAddress = true
    Address addr3 = 
        createTestAddress("Street 3", "3", 
        "City 3", "300", countryMapper.toEntity(countryDto));
    PersonAddressDto payload3 = 
        createPersonAddressPayload(addr3.getId(), seededPersonId, 
        addressMapper.toDto(addr3), true);

    MvcResult res3 = mockMvc.perform(post(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(payload3)))
            .andExpect(status().isCreated())
            // WHY CHECK HERE: 
            // Ensure the third creation operation also successfully 
            // registers as the new active main address right out of the gate.
            .andExpect(jsonPath("$.data.mainAddress").value(true))
            .andReturn();
            
    // Re-applying the HATEOAS "url thing" to dynamically 
    // extract the third resource's self link.
    String url3 = JsonPath.read(res3.getResponse().getContentAsString(), "$.data._links.self.href");

    // 5. Confirm final toggle state across all three addresses
    // WHY: This is our final verification matrix 
    // ensuring total data consistency:
    // - selfUrl (Address #1) remains demoted (false)
    // - url2 (Address #2) was correctly demoted to false 
    //                          when Address #3 became main
    // - url3 (Address #3) holds the active main status (true)
    mockMvc.perform(get(selfUrl))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.mainAddress").value(false));

    mockMvc.perform(get(url2))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.mainAddress").value(false));

    mockMvc.perform(get(url3))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.mainAddress").value(true));
    }
    

    /**
     * Helper method to settle a person at an address (POST), 
     * asserting a 201 Created status and returning the HATEOAS self URL.
     */
    private String settlePersonAtAddress(
            PersonData person, 
            Address address, 
            boolean mainAddress) throws Exception {
        MvcResult result = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    createPersonAddressPayload(
                        address.getId(), 
                        person.id(), 
                        addressMapper.toDto(address), 
                        mainAddress))))
                .andExpect(status().isCreated())
                .andReturn();
        return JsonPath.read(
            result.getResponse().getContentAsString(), 
            "$.data._links.self.href");
    }

    /**
     * Helper method to assert all address properties and main address status 
     * against a given expected Address entity object using a descriptive resource URL.
     */
    private void assertPersonAddress(
            String personAddressUrl, 
            Address expectedAddress, 
            boolean expectedMainAddress) throws Exception {
        mockMvc.perform(get(personAddressUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.address.id")
                    .value(expectedAddress.getId()))
                .andExpect(jsonPath("$.data.address.street")
                    .value(expectedAddress.getStreet()))
                .andExpect(jsonPath("$.data.address.houseNumber")
                    .value(expectedAddress.getHouseNumber()))
                .andExpect(jsonPath("$.data.address.city")
                    .value(expectedAddress.getCity()))
                .andExpect(jsonPath("$.data.address.zipCode")
                    .value(expectedAddress.getZipCode()))
                .andExpect(jsonPath("$.data.mainAddress")
                    .value(expectedMainAddress));
    }

    /**
     * Verifies a complex co-living lifecycle: two persons share an 
     * initial apartment (sharedApartment), both independently move out 
     * by registering new solo apartments (earlySoloApartment and lateSoloApartment) 
     * which invalidates their old links with 404 checks, and finally they both 
     * reunite by moving back to the original sharedApartment.
     */
    @Test
    public void testSharedAddress_complexMovingSequenceAndRejoining() 
            throws Exception {
        // 1. Create Early Mover and Late Mover using the test person helper
        PersonData earlyMover = createTestPerson(
            "Early", "Mover", 11112222L, LocalDate.of(1990, 1, 1));
        PersonData lateMover = createTestPerson(
            "Late", "Mover", 33334466L, LocalDate.of(1992, 2, 2));

        Address sharedApartment = createTestAddress(
            "Shared St", "1", "Metro City", "1010", 
            countryMapper.toEntity(countryDto));

        // Early Mover and Late Mover settle into shared apartment using helper
        String earlyMoverSharedUrl = 
            settlePersonAtAddress(earlyMover, sharedApartment, true);
        String lateMoverSharedUrl = 
            settlePersonAtAddress(lateMover, sharedApartment, true);

        // CONFIRM STEP 1: Both are settled correctly in sharedApartment
        assertPersonAddress(
            earlyMoverSharedUrl, sharedApartment, true);
        assertPersonAddress(
            lateMoverSharedUrl, sharedApartment, true);

        // 2. Early Mover moves out by registering solo apartment (service handles old link removal)
        Address earlySoloApartment = createTestAddress(
            "Early Solo St", "2", "Metro City", "2020", 
            countryMapper.toEntity(countryDto));
        
        String earlyMoverSoloUrl = 
            settlePersonAtAddress(earlyMover, earlySoloApartment, true);

        // CONFIRM STEP 2: Early Mover is no longer present at their old shared address (404), 
        // they are verified at their solo apartment, and Late Mover is still safely at shared apartment
        mockMvc.perform(get(earlyMoverSharedUrl))
                .andExpect(status().isNotFound());
        //here you should confirm that: Early Mover is no longer in sharedAppartment

        assertPersonAddress(
            earlyMoverSoloUrl, earlySoloApartment, true);
        assertPersonAddress(
            lateMoverSharedUrl, sharedApartment, true);

        // 3. Late Mover moves out by registering solo apartment (service handles old link removal)
        Address lateSoloApartment = createTestAddress(
            "Late Solo St", "3", "Metro City", "3030", 
            countryMapper.toEntity(countryDto));

        String lateMoverSoloUrl = 
            settlePersonAtAddress(lateMover, lateSoloApartment, true);

        // CONFIRM STEP 3: Late Mover is no longer present at their old shared address (404), 
        // they are verified at their solo apartment, and Early Mover is verified at solo apartment
        mockMvc.perform(get(lateMoverSharedUrl))
                .andExpect(status().isNotFound());
        //here you should confirm that: Late Mover is no longer in sharedAppartment

        assertPersonAddress(
            lateMoverSoloUrl, lateSoloApartment, true);
        assertPersonAddress(
            earlyMoverSoloUrl, earlySoloApartment, true);

        // 4. Early Mover moves back to original shared apartment
        String earlyMoverRejoinedUrl = 
            settlePersonAtAddress(earlyMover, sharedApartment, true);

        //here you should check, that:  Early Mover is no longer inside: earlySoloApartment
        mockMvc.perform(get(earlyMoverSoloUrl))
                .andExpect(status().isNotFound());

        // CONFIRM STEP 4: Early Mover is back at sharedApartment
        assertPersonAddress(
            earlyMoverRejoinedUrl, sharedApartment, true);

        // 5. Late Mover moves back to shared apartment, completing reunion
        String lateMoverRejoinedUrl = 
            settlePersonAtAddress(lateMover, sharedApartment, true);

        //here you should check, that:  Late Mover is no longer inside: lateSoloApartment
        mockMvc.perform(get(lateMoverSoloUrl))
                .andExpect(status().isNotFound());

        // CONFIRM STEP 5: Both reunited back together at sharedApartment
        assertPersonAddress(
            earlyMoverRejoinedUrl, sharedApartment, true);
        assertPersonAddress(
            lateMoverRejoinedUrl, sharedApartment, true);
    }

    @Test
    public void testDelete_sharedAddressShouldNotAffectOtherPersonsLink() throws Exception {
        PersonData personB = createTestPerson("Room", "Mate", 99998888L, LocalDate.of(1992, 2, 2));

        PersonAddressDto payloadB = createPersonAddressPayload(seededAddressId, personB.id(), seededAddressDto, true);
        MvcResult postResB = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payloadB)))
                .andExpect(status().isCreated())
                .andReturn();
        String urlPersonB = JsonPath.read(postResB.getResponse().getContentAsString(), "$.data._links.self.href");

        mockMvc.perform(delete(selfUrl))
                .andExpect(status().isOk());

        mockMvc.perform(get(selfUrl))
                .andExpect(status().isNotFound());

        mockMvc.perform(get(urlPersonB))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.address.street").value("Setup St"));
    }

}
