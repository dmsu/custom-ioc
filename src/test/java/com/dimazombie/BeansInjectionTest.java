package com.dimazombie;

import com.dimazombie.testing.autosearch.Sponsor;
import com.dimazombie.testing.injection.Organizer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BeansInjectionTest {

    @Test
    void shouldRegisterBeanWhenAutoSearchIsEnabled(){
        //GIVEN
        DependenciesConfig config = new DependenciesConfig("com.dimazombie.testing.injection");
        //WHEN
        Organizer organizer = new Injector(config).get(Organizer.class);
        //THEN
        Assert.assertNotNull(organizer);
        Assert.assertNotNull(organizer.getAssistant());
    }
}
