package ibee.webapp.todo_app.core.repository.baseRepo.entityMangerBased;

import org.springframework.data.repository.NoRepositoryBean;

/*
    Falls die ID im Code erzeugt wird, so ist dieses Interfaces performanter,
    da persist operationen, davon ausgehen, dass es keinen db eintrag gibt..
    dieser wird also niemals gesucht im gegenzug zur save implementierung
    SelfDefinedId id = new UUID(); --> id != null --> kein select statement 

    Dieses Interface is nicht notwendig, 
    wenn die selbst definierte Id mit Hibernate erzeugt wird:
    Beispiel:
    @Id
    @GeneratedValue
    private UUID id;

*/
@NoRepositoryBean
public interface MyCrudRepoIdsForSimpleKeysWhichAreSelfDefinedKeys<T> extends MyReadDeleteForSimpleIdKeys<T>, MyPersistAndMergeRepository<T> {

}
