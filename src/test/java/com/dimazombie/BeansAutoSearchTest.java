package com.dimazombie;

import com.dimazombie.testing.autosearch.Sponsor;
import com.dimazombie.testing.typed.Visitor;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BeansAutoSearchTest {

    @Test
    void shouldRegisterBeanWhenAutoSearchIsEnabled(){
        //GIVEN
        DependenciesConfig config = new DependenciesConfig("com.dimazombie.testing.autosearch");
        //WHEN
        Sponsor sponsor = new Injector(config).get(Sponsor.class);
        //THEN
        Assert.assertNotNull(sponsor);
    }

    @Test
    void shouldRegisterBeanByTypeWhenAutoSearchIsEnabled(){
        //GIVEN
        DependenciesConfig config = new DependenciesConfig("com.dimazombie.testing.typed");
        //WHEN
        Visitor sponsor = new Injector(config).get(Visitor.class);
        //THEN
        Assert.assertNotNull(sponsor);
    }
}
