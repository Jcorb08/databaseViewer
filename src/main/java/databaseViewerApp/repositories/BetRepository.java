package databaseViewerApp.repositories;

import databaseViewerApp.models.Bet;
import databaseViewerApp.models.Client;
import databaseViewerApp.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BetRepository extends JpaRepository<Bet, Integer>, JpaSpecificationExecutor<Bet> {

    // single
    public final static String SELECT_GAME = "SELECT b FROM Bet b WHERE game_id = :game_id";
    @Query(SELECT_GAME)
    List<Bet> findByGame(@Param("game_id") Game game_id);

    public final static String SELECT_CLIENT = "SELECT b FROM Bet b WHERE client_id = :client_id";
    @Query(SELECT_CLIENT)
    List<Bet> findByClient(@Param("client_id") Client client_id);

    public final static String SELECT_DATE = "SELECT b FROM Bet b WHERE date = :date";
    @Query(SELECT_DATE)
    List<Bet> findByDate(@Param("date") Date date);

    // double
    public final static String SELECT_GAME_CLIENT = "SELECT b FROM Bet b WHERE game_id = :game_id AND client_id = :client_id";
    @Query(SELECT_GAME_CLIENT)
    List<Bet> findByGameAndClient(@Param("game_id") Game game_id, @Param("client_id") Client client_id);

    public final static String SELECT_GAME_DATE = "SELECT b FROM Bet b WHERE game_id = :game_id AND date = :date";
    @Query(SELECT_GAME_DATE)
    List<Bet> findByGameAndDate(@Param("game_id") Game game_id, @Param("date") Date date);

    public final static String SELECT_DATE_CLIENT = "SELECT b FROM Bet b WHERE date = :date AND client_id = :client_id";
    @Query(SELECT_DATE_CLIENT)
    List<Bet> findByDateAndClient(@Param("date") Date date, @Param("client_id") Client client_id);

    //triple
    public final static String SELECT_DATE_CLIENT_GAME = "SELECT b FROM Bet b WHERE date = :date AND client_id = :client_id AND game_id = :game_id";
    @Query(SELECT_DATE_CLIENT_GAME)
    List<Bet> findByDateAndClientAndGame(@Param("date") Date date, @Param("game_id") Game game_id, @Param("client_id") Client client_id);
}