package com.zikozee.graphql.comonent.problemz;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.zikozee.graphql.generated.DgsConstants;
import com.zikozee.graphql.generated.types.SearchItemFilter;
import com.zikozee.graphql.generated.types.SearchableItem;

import java.util.List;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */


@DgsComponent
public class ItemSearchDataResolver {

    @DgsQuery(field = DgsConstants.QUERY.ItemSearch)
    public List<SearchableItem> searchItems(@InputArgument(name = "filter",
            collectionType = SearchItemFilter.class) SearchItemFilter searchItemFilter){
        return null;
    }
}
