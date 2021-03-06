package net.ideaslibres.superstore.controller;

import net.ideaslibres.superstore.configuration.ApiValidation;
import net.ideaslibres.superstore.exception.InvalidDataException;
import net.ideaslibres.superstore.exception.RecordNotFoundException;
import net.ideaslibres.superstore.model.dto.ItemDto;
import net.ideaslibres.superstore.service.ItemsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

import static net.ideaslibres.superstore.model.Constants.ROLE_ADMIN;

@RestController
@RequestMapping("/items")
@Validated({ApiValidation.class})
public class ItemsController {

    private static final Logger logger = LoggerFactory.getLogger(ItemsController.class);

    private ItemsService service;

    public ItemsController(ItemsService service) {
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<ItemDto>> getItems(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "desc(price)")
                List<@Pattern(regexp = "(desc|asc)\\([a-zA-Z]+\\)") String> orderBy,
            @RequestParam(required = false, defaultValue = "8") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber
    ) {
        return ResponseEntity.ok(service.getItems(search, orderBy, pageSize, pageNumber));
    }

    @RolesAllowed(ROLE_ADMIN)
    @GetMapping(value = "/{itemcode}", produces = "application/json")
    public ResponseEntity<ItemDto> getItem(
            @PathVariable(required = true) String itemcode
    ) throws RecordNotFoundException {
        return ResponseEntity.ok(service.getItemByCode(itemcode));
    }

    @RolesAllowed(ROLE_ADMIN)
    @PostMapping
    public ResponseEntity<Iterable<ItemDto>> postItems(@Valid @RequestBody List<ItemDto> items) throws InvalidDataException {
        logger.info("Creating and/or updating {} items.", items.size());
        return ResponseEntity.ok(service.saveOrUpdateItems(items));
    }

    @RolesAllowed(ROLE_ADMIN)
    @PutMapping
    public ResponseEntity<ItemDto> putItem(@RequestBody ItemDto item) throws InvalidDataException, RecordNotFoundException {
        logger.info("Updating item {}.", item.code);
        return ResponseEntity.ok(service.updateItem(item));
    }
}
