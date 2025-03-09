package ir.smarttrustco.cryptography.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<E extends BaseEntity<P>, P extends Number, S extends BaseService<E, P>> {

    protected static final String SAVE = "/save";
    protected static final String FIND_BY_ID = "/find-by-id/{id}";
    // protected static final String FIND_ALL = "/find-all";
    // protected static final String DELETE = "/delete";
    // protected static final String UPDATE = "/update";

    protected S service;

    public BaseController(S service) {
        this.service = service;
    }

    @PostMapping(SAVE)
    public ResponseEntity<?> save(@RequestBody E entity) {
        service.save(entity);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<?> findById(@PathVariable P id) {
        E findEntity = service.findById(id);
        return new ResponseEntity<>(findEntity, HttpStatus.OK);
    }
}
