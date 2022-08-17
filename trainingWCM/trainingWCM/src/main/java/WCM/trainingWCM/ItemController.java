package WCM.trainingWCM;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/items")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController {

    @Autowired
    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    public @ResponseBody List<Item> all() {
        return repository.findAll();
//        List<EntityModel<Item>> items = repository.findAll().stream()
//                .map(item -> EntityModel.of(item,
//                        linkTo(methodOn(ItemController.class).one(item.getId())).withSelfRel(),
//                        linkTo(methodOn(ItemController.class).all()).withRel("items")))
//                .collect(Collectors.toList());
//
//        return CollectionModel.of(items, linkTo(methodOn(ItemController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping
    public ResponseEntity newItem(@RequestBody Item newItem) throws URISyntaxException {
        Item savedItem = repository.save(newItem);
        return ResponseEntity.created(new URI("/items/" + savedItem.getId())).body(savedItem);
    }

    // Single item
    @GetMapping("/{id}")
    public Item one(@PathVariable Long id) {
            return repository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity replaceItem(@RequestBody Item newItem, @PathVariable Long id) {

        return repository.findById(id)
                .map(item -> {
                    item.setName(newItem.getName());
                    item.setPrice(newItem.getPrice());
                    item.setRating(newItem.getRating());
                    repository.save(item);
                    Item currentItem = repository.findById(id).orElseThrow(RuntimeException::new);
                    return ResponseEntity.ok(currentItem);
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    repository.save(newItem);
                    Item currentItem = repository.findById(id).orElseThrow(RuntimeException::new);
                    return ResponseEntity.ok(currentItem);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}