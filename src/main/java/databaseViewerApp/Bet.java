package databaseViewerApp;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Bet {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private Date date;

    private Integer number_of_bets;

    private Integer stake;

    private Integer returns;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clientId", referencedColumnName = "id")
    private Client client_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gameId", referencedColumnName = "id")
    private Game game_id;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNumber_of_bets() {
        return number_of_bets;
    }

    public void setNumber_of_bets(Integer number_of_bets) {
        this.number_of_bets = number_of_bets;
    }

    public Integer getStake() {
        return stake;
    }

    public void setStake(Integer stake) {
        this.stake = stake;
    }

    public Integer getReturns() {
        return returns;
    }

    public void setReturns(Integer returns) {
        this.returns = returns;
    }

    public Client getClient_id() {
        return client_id;
    }

    public void setClient_id(Client client_id) {
        this.client_id = client_id;
    }

    public Game getGame_id() {
        return game_id;
    }

    public void setGame_id(Game game_id) {
        this.game_id = game_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
