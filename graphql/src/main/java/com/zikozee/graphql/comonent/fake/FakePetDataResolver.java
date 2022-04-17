package com.zikozee.graphql.comonent.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.zikozee.graphql.datasource.fake.FakeMobileAppDataSource;
import com.zikozee.graphql.datasource.fake.FakePetDataSource;
import com.zikozee.graphql.generated.DgsConstants;
import com.zikozee.graphql.generated.types.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: Ezekiel Eromosei
 * @created: 17 April 2022
 */

@DgsComponent
public class FakePetDataResolver {

//    @DgsData(
//            parentType = DgsConstants.QUERY_TYPE,
//            field = DgsConstants.QUERY.Pets
//    )
//    public List<MobileApp> getMobileApps(@InputArgument(name = "petFilter", collectionType = PetFilter.class)
//                                             Optional<PetFilter> filter) {


    @DgsQuery(field = DgsConstants.QUERY.Pets)
    public List<Pet> getPets(@InputArgument(collectionType = PetFilter.class)
                                         Optional<PetFilter> petFilter) {
        if (petFilter.isEmpty()) return FakePetDataSource.PET_LIST;

        return FakePetDataSource.PET_LIST.stream()
                .filter(pet -> this.matchFilter(petFilter.get(), pet))
                .collect(Collectors.toList());
    }

    private boolean matchFilter(PetFilter petFilter, Pet pet) {

        if(StringUtils.isBlank(petFilter.getPetType())) return true;

        if(petFilter.getPetType().equalsIgnoreCase(Dog.class.getSimpleName())){
            return pet instanceof Dog;
        }else if (petFilter.getPetType().equalsIgnoreCase(Cat.class.getSimpleName())){
            return pet instanceof Cat;
        }else return false;
    }
}
