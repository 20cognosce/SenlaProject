package task3.prog3;

public class AssemblyStepBody implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        return new Body();
    }
}
