package unsw.loopmania.Buildings;

/**
 * Building interface is used by the frontend to place buildings
 * so it doesn't have to know what building it is placing. This removes
 * coupling between the frontend and backend which is always good. It is
 * also used by the BuildingFactory so that classes that use the factory
 * don't need to account for different return types.
 * @author Group FRIDGE
 */
public interface Building {
    
}
