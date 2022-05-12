package com.game.controller;

import com.game.entity.Player;

import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import com.game.service.SearchCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PlayerController {

    // Прокинули поля Сервиса в контроллер через конструктор
    private final PlayerService playerService;
    //@Autowired. Контроллеры — эти бины Spring, которые являются синглтонами и предназначены для взаимодествия с
    // пользователем через DispatcherServlet.
    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

        //GetMaping get представление странички по URL "/rest/players"
        //@RequestParam(name = "name", required = false ожидам что в запросе get по пад параметром name  получим что-то  "?
        //required = false - тип необязательно получим
        //
        @GetMapping(value = "/rest/players")    // URL , Method GET , URL Params

        //ResponseEntity Extension of HttpEntity that adds a HttpStatus status code. Used in RestTemplate as well @Controller methods.
        // метод возвращает сущностьовтета Листо Player
        //ожидвет, что получит Все параметры в скобках (RequestParam)...Get запрос
        //playerServise Фильтрует по page, size, order = defaultValue
        //playerServise Фильтрует по banned, name, title, race, profession, minLevel,
        // maxLevel,minExperience, maxExperience, before, after - необязательно(required = false)

        public ResponseEntity<List<Player>> getAll(@RequestParam(name = "pageNumber", defaultValue = "0") int page,
                                                   @RequestParam(name = "pageSize", defaultValue = "3") int size,
                                                   @RequestParam(name = "order", defaultValue = "ID") PlayerOrder order,
                                                   @RequestParam(name = "banned", required = false) Boolean banned,
                                                   @RequestParam(name = "name", required = false) String name,
                                                   @RequestParam(name = "title", required = false) String title,
                                                   @RequestParam(name = "race", required = false) Race race,
                                                   @RequestParam(name = "profession", required = false) Profession profession,
                                                   @RequestParam(name = "minLevel", required = false) Integer minLevel,
                                                   @RequestParam(name = "maxLevel", required = false) Integer maxLevel,
                                                   @RequestParam(name = "minExperience", required = false) Integer minExperience,
                                                   @RequestParam(name = "maxExperience", required = false) Integer maxExperience,
                                                   @RequestParam(name = "before", required = false) Long before,
                                                   @RequestParam(name = "after", required = false) Long after
        ) {
            Pageable pageable = playerService.getPageable(page, size, order);
            List<SearchCriteria> filters = playerService.getFilters(banned, name, title, race, profession, minLevel, maxLevel,
                    minExperience, maxExperience, before, after);

            return playerService.getAll(filters, pageable);
        }

    @GetMapping(value = "/rest/players/count")
    public ResponseEntity<Integer> getPlayersCount(@RequestParam(name = "pageNumber", defaultValue = "0") int page,
                                                   @RequestParam(name = "pageSize", defaultValue = "3") int size,
                                                   @RequestParam(name = "order", defaultValue = "ID") PlayerOrder order,
                                                   @RequestParam(name = "banned", required = false) Boolean banned,
                                                   @RequestParam(name = "name", required = false) String name,
                                                   @RequestParam(name = "title", required = false) String title,
                                                   @RequestParam(name = "race", required = false) Race race,
                                                   @RequestParam(name = "profession", required = false) Profession profession,
                                                   @RequestParam(name = "minLevel", required = false) Integer minLevel,
                                                   @RequestParam(name = "maxLevel", required = false) Integer maxLevel,
                                                   @RequestParam(name = "minExperience", required = false) Integer minExperience,
                                                   @RequestParam(name = "maxExperience", required = false) Integer maxExperience,
                                                   @RequestParam(name = "before", required = false) Long before,
                                                   @RequestParam(name = "after", required = false) Long after) {
        Pageable pageable = playerService.getPageable(page, size, order);

        List<SearchCriteria> filters = playerService.getFilters(banned, name, title, race, profession, minLevel, maxLevel,
                minExperience, maxExperience, before, after);
        return playerService.getPlayersCount(filters, pageable);
    }

    // Post запрос в УРЛ /rest/players"
    // Ожтдаем что получим плеера(нового плеера для Сервиса)
    // и на бекенд вернеться сучщность ответа() Player  т.к. RequestBody
    @PostMapping(value = "/rest/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }
    // get запрос в УРЛ /rest/players" Через строку запроса
    // Ожидаем что закинем на УРЛ Plaer
    // и на бекенд вернеться ответ сущьность(extends HttpEntity<T>) плеер
    @GetMapping(value = "/rest/players/{id}")
    public ResponseEntity<?> getPlayerByID(@PathVariable(name = "id") String id) {
        return playerService.getPlayerByID(id);
    }
    // get запрос в УРЛ /rest/players" Через строку запроса
    //Получаем  ИД через строку зароса, И ожидаем что получим плеера(нового плеера для Сервиса)
    // и на бекенд вернеться ответ сущность плеер(extends HttpEntity<T>) т.к. RequestBody
    @PostMapping(value = "/rest/players/{id}")
    public ResponseEntity<?> updatePlayerByID(@PathVariable(name = "id") String id,
                                              @RequestBody Player player) {
        return playerService.updatePlayerByID(id, player);
    }
    //  Через строку запроса
    // Получаем ИД из строки запроса
    // и на бекенд вернеться плеер(extends HttpEntity<T>)
    @DeleteMapping(value = "/rest/players/{id}")
    public ResponseEntity<?> deletePlayerById(@PathVariable(name = "id") String id) {
        return playerService.deletePlayerById(id);
    }


}