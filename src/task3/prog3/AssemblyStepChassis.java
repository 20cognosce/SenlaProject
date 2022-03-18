package task3.prog3;

public class AssemblyStepChassis implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        return new Chassis();
    }
}
