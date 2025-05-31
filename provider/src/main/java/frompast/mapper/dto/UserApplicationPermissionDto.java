package frompast.mapper.dto;

public record UserApplicationPermissionDto(Long id,

                                           GroupReadDto group,

                                           ApplicationReadDto application,

                                           PermissionReadDto permission) {

}
