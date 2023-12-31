package io.bootify.musical_store.util;

import io.bootify.musical_store.accessories.Accessories;
import io.bootify.musical_store.accessories.AccessoriesDTO;
import io.bootify.musical_store.instrument.Instrument;
import io.bootify.musical_store.instrument.InstrumentDTO;
import io.bootify.musical_store.user.User;
import io.bootify.musical_store.user.UserDTO;

public class DTOConverter {

    public static AccessoriesDTO convert (Accessories accessories) {
        AccessoriesDTO accessoriesDTO = new AccessoriesDTO();
        accessoriesDTO.setId(accessories.getId());
        accessoriesDTO.setDepartment(accessoriesDTO.getDepartment());
        accessoriesDTO.setName(accessoriesDTO.getName());
        accessoriesDTO.setPrice(accessories.getPrice());
        return accessoriesDTO;
    }

    public static InstrumentDTO convert (Instrument instrument) {
        InstrumentDTO instrumentDTO = new InstrumentDTO();
        instrumentDTO.setId(instrument.getId());
        instrumentDTO.setFamily(instrument.getFamily());
        instrumentDTO.setType(instrument.getType());
        instrumentDTO.setModel(instrument.getModel());
        instrumentDTO.setPrice(instrument.getPrice());
        return instrumentDTO;
    }

    public static UserDTO convert (User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setDocumentNumber(user.getDocumentNumber());
        userDTO.setAdress(user.getAdress());
        userDTO.setCredit(user.getCredit());
        return userDTO;
    }

}
