package io.bootify.musical_store.user;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByDocumentNumberIgnoreCase(String documentNumber);

}
