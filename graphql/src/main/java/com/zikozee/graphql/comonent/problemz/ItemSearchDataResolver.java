package com.zikozee.graphql.comonent.problemz;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import com.zikozee.graphql.generated.DgsConstants;
import com.zikozee.graphql.generated.types.SearchItemFilter;
import com.zikozee.graphql.generated.types.SearchableItem;
import com.zikozee.graphql.service.query.ProblemzQueryService;
import com.zikozee.graphql.service.query.SolutionzQueryService;
import com.zikozee.graphql.util.GraphqlBeanMapper;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Ezekiel Eromosei
 * @created: 18 April 2022
 */


@DgsComponent
@RequiredArgsConstructor
public class ItemSearchDataResolver {

    private final ProblemzQueryService problemzQueryService;

    private final SolutionzQueryService solutionzQueryService;

    @DgsQuery(field = DgsConstants.QUERY.ItemSearch)
    public List<SearchableItem> searchItems(@InputArgument(name = "filter",
            collectionType = SearchItemFilter.class) SearchItemFilter searchItemFilter){

        var keyword = searchItemFilter.getKeyword();

        List<SearchableItem> searchableItems = new ArrayList<>();

        var problemzByKeyword = problemzQueryService.problemzByKeyword(keyword)
                .stream().map(GraphqlBeanMapper::mapToGraphql).collect(Collectors.toList());

        searchableItems.addAll(problemzByKeyword);

        var solutionzByKeyword = solutionzQueryService.solutionzByKeyword(keyword)
                .stream().map(GraphqlBeanMapper::mapToGraphql).collect(Collectors.toList());

        searchableItems.addAll(solutionzByKeyword);

        if(searchableItems.isEmpty()) throw new DgsEntityNotFoundException("No item with keyword " + keyword);

        searchableItems.sort(Comparator.comparing(SearchableItem::getCreatedDateTime).reversed());

        return searchableItems;
    }
}
