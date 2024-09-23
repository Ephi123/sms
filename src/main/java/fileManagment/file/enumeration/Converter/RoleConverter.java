package fileManagment.file.enumeration.Converter;

import fileManagment.file.enumeration.Authority;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.validation.constraints.Null;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Authority,String> {
    @Override
    public String convertToDatabaseColumn(Authority authority) {
        if (authority == null)
            return null;
        return authority.getRole();
    }

    @Override
    public Authority convertToEntityAttribute(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }

        return Stream.of(Authority.values())
                .filter(authority -> authority.getRole().equals(code))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
