package databaseViewerApp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface GameRepository extends JpaRepository<Game,Integer>, JpaSpecificationExecutor<Game> {

    public final static String GET_GAME_FROM_NAME = "SELECT g FROM Game g WHERE name = :name";

    @Query(GET_GAME_FROM_NAME)
    Optional<Game> findByName(@Param("name") String gameName);
}