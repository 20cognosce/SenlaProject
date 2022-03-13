package task3.prog3;

public class Car implements IProduct {
    @Override
    public void installFirstPart(IProductPart iProductPart) {
        System.out.println(iProductPart.getClass().getSimpleName() + " has been installed");
    }

    @Override
    public void installSecondPart(IProductPart iProductPart) {
        System.out.println(iProductPart.getClass().getSimpleName() + " has been installed");
    }

    @Override
    public void installThirdPart(IProductPart iProductPart) {
        System.out.println(iProductPart.getClass().getSimpleName() + " has been installed");
    }
}
