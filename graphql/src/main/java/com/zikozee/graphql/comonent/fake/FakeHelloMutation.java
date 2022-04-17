package com.zikozee.graphql.comonent.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.zikozee.graphql.datasource.fake.FakeHelloDataSource;
import com.zikozee.graphql.generated.DgsConstants;
import com.zikozee.graphql.generated.types.Hello;
import com.zikozee.graphql.generated.types.HelloInput;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Ezekiel Eromosei
 * @created: 17 April 2022
 */

@DgsComponent
public class FakeHelloMutation {

//    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.AddHello)
    @DgsMutation(field = DgsConstants.MUTATION.AddHello)
    public int addHello(@InputArgument(name = "helloInput") HelloInput helloInput) {

        var hello = Hello.newBuilder().text(helloInput.getText())
                .randomNumber(helloInput.getNumber()).build();

        FakeHelloDataSource.HELLO_LIST.add(hello);

        return FakeHelloDataSource.HELLO_LIST.size();
    }

    @DgsMutation(field = DgsConstants.MUTATION.ReplaceHelloText)
    public List<Hello> replaceHelloText(@InputArgument(name = "helloInput") HelloInput helloInput) {

        FakeHelloDataSource.HELLO_LIST.stream()
                .filter(h -> h.getRandomNumber() == helloInput.getNumber())
                .forEach(h -> h.setText(helloInput.getText()));

        return FakeHelloDataSource.HELLO_LIST;
    }


    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.DeleteHello)
    public int deleteHello(int number) {

        FakeHelloDataSource.HELLO_LIST.removeIf(h -> h.getRandomNumber()== number);

        return FakeHelloDataSource.HELLO_LIST.size();
    }
}
