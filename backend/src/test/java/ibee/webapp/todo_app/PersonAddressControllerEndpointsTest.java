package ibee.webapp.todo_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import ibee.webapp.todo_app.core.dto.AddressDto;
import ibee.webapp.todo_app.core.dto.CountryDto;
import ibee.webapp.todo_app.core.entity.Address;
import ibee.webapp.todo_app.core.entity.Country;
import ibee.webapp.todo_app.core.service.AddressServiceImpl;
import ibee.webapp.todo_app.core.service.CountryServiceImpl;
import ibee.webapp.todo_app.features.person.dto.PersonData;
import ibee.webapp.todo_app.features.person.related.contact.PersonAddressDto;
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
    private String selfUrl;
    private String detailsUrl;
    private CountryDto countryDto;

    @BeforeEach
    public void setupRealPersonAndAddress() throws Exception {
        // 1. Create Real Person
        // 1. Utilize helper method to create a baseline Person
        PersonData savedPerson = createTestPerson("Test", "User", 123401011990L, LocalDate.of(1990, 1, 1));
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
        Address savedAddress = createTestAddress("Setup St", "1", "Setup City", "100", realCountry);
        assertNotNull(savedAddress.getId());
        seededAddressId = savedAddress.getId();
        AddressDto addressDto = addressMapper.toDto(savedAddress);

        // 4. Build payload using correct composite ID constructor order: (addressId, personId)
        PersonAddressDto dtoPayload = createPersonAddressPayload(seededAddressId, seededPersonId, addressDto, true);
        
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
        AddressDto updatedAddress = new AddressDto(null, "Updated St", "2", "Updated City", "200", countryDto);
        
        // Correct parameter ordering via helper method eliminates null-pointer vulnerabilities
        PersonAddressDto updatePayload = createPersonAddressPayload(seededAddressId, seededPersonId, updatedAddress, false);

        
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
}
