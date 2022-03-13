package task3.prog3;

public class AssemblyStepEngine implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        return new Engine();
    }
}
