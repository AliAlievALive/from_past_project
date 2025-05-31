package plant.mapper.dto;

import java.util.UUID;

record GroupReadDto (UUID id, String owner, String name){
}