package task3.prog3;

public class AssemblyLine implements IAssemblyLine {
    private final ILineStep chassisStep;
    private final ILineStep engineStep;
    private final ILineStep bodyStep;

    @Override
    public IProduct assembleProduct(IProduct newCar) {
        newCar.installFirstPart(chassisStep.buildProductPart());
        newCar.installSecondPart(engineStep.buildProductPart());
        newCar.installThirdPart(bodyStep.buildProductPart());
        return newCar;
    }

    public AssemblyLine(ILineStep chassisStep, ILineStep engineStep, ILineStep bodyStep) {
        this.chassisStep = chassisStep;
        this.engineStep = engineStep;
        this.bodyStep = bodyStep;
    }
}
