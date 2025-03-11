package ir.smarttrustco.cryptography.person;

import ir.smarttrustco.cryptography.basic.BaseServiceImpl;
import ir.smarttrustco.cryptography.cryptography.SymmetricEncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl extends BaseServiceImpl<PersonEntity, Long, PersonRepository> implements PersonService {
    private final SymmetricEncryptionService symmetricEncryption;

    @Autowired
    public PersonServiceImpl(PersonRepository repository, SymmetricEncryptionService symmetricEncryption) {
        super(repository);
        this.symmetricEncryption = symmetricEncryption;
    }

    @Override
    public void save(PersonEntity entity) {
        entity.setSsn(symmetricEncryption.encryptWithSymmetric(entity.getPhoneNumber(), entity.getSsn()));
        super.save(entity);
    }

    @Override
    public PersonEntity findById(Long id) {
        PersonEntity entity = super.findById(id);
        entity.setSsn(symmetricEncryption.decryptWithSymmetric(entity.getPhoneNumber(), entity.getSsn()));
        return entity;
    }

//    public List<PersonEntity> findAll() {
//        List<PersonEntity> entities = super.findAll();
//        return !entities.isEmpty() ? entities.stream()
//                .peek((entity)->entity.setSsn(symmetricEncryption.decryptWithSymmetric(entity.getPhoneNumber(),entity.getSsn()))).toList() : null;
//    }

}
