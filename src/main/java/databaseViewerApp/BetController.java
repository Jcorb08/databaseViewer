package databaseViewerApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class BetController {
    private final BetService betService;
    private final BetRepository betRepository;

    @Autowired
    public BetController(BetService betService, BetRepository betRepository) {
        this.betService = betService;
        this.betRepository = betRepository;
    }

    @PostMapping("/insertFromJson")
    public void insertUsersFrom(@RequestParam String filepath){
        betService.insertUsersFromJsonFile(filepath);
    }

    @GetMapping(path="/")
    public @ResponseBody Iterable<Bet> getAllUsers() {
        // This returns a JSON or XML with the users
        return betRepository.findAll();
    }
}
