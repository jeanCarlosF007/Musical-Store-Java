package io.bootify.musical_store.instrument;

import org.springframework.data.jpa.repository.JpaRepository;


public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
}
