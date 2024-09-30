package fileManagment.file.repository;

import fileManagment.file.entity.PaymentCollection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCollectionRepo extends JpaRepository<PaymentCollection,Long> {
}
