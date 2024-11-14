package site;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import property.enums.PropertyType;
import property.enums.Service;
import user.Tenant;
import user.User;

class SiteRegisterTest {

    private SiteRegister siteRegister;
    private Set<Category> categoriesTenant;
    private Set<Category> categoriesOwner;
    private Set<Category> categoriesProperty;

    @BeforeEach
    void setUp() {
        categoriesTenant = new HashSet<>();
        categoriesOwner = new HashSet<>();
        categoriesProperty = new HashSet<>();

        siteRegister = new SiteRegister(categoriesTenant, categoriesOwner, categoriesProperty);
    }

    @Test
    void testAddPropertyType() {
        PropertyType propertyType = PropertyType.APARTMENT;
        siteRegister.addPropertyType(propertyType);

        assertTrue(siteRegister.getPropertyTypes().contains(propertyType), "PropertyType should be added to the set");
    }

    @Test
    void testAddService() {
        Service service = Service.AIR_CONDITIONING;
        siteRegister.addService(service);

        assertTrue(siteRegister.getServices().contains(service), "Service should be added to the set");
    }

    @Test
    void testRegisterUser() {
        User user = new Tenant("John Doe", "johndoe@example.com", 123456789);
        LocalDate today = LocalDate.now();

        siteRegister.registerUser(user, today);

        assertTrue(siteRegister.isRegistered(user), "User should be registered in SiteRegister");
        assertEquals(today, user.getRegisterDate(), "User register date should match the provided date");
    }

    @Test
    void testIsRegistered() {
        User user = new Tenant("Jane Doe", "janedoe@example.com", 987654321);
        assertFalse(siteRegister.isRegistered(user), "User should not be registered initially");

        LocalDate today = LocalDate.now();
        siteRegister.registerUser(user, today);

        assertTrue(siteRegister.isRegistered(user), "User should be registered after calling registerUser");
    }

    @Test
    void testGetTenants() {
        User regularUser = new Tenant("John Smith", "johnsmith@example.com", 555555555);
        Tenant tenantUser = new Tenant("Jane Smith", "janesmith@example.com", 444444444);

        siteRegister.registerUser(regularUser, LocalDate.now());
        siteRegister.registerUser(tenantUser, LocalDate.now());

        List<User> tenants = siteRegister.getTenants();

        assertEquals(2, tenants.size(), "Only tenants should be returned");
        assertTrue(tenants.contains(tenantUser), "Tenant should be included in the result");
        assertTrue(tenants.contains(regularUser), "Regular tenant should also be included in the result");
    }
}
