package task3.prog3;

//Variant 1 - car

public class Main {
    public static void main(String[] args) {
        AssemblyStepChassis assemblyChassis = new AssemblyStepChassis();
        AssemblyStepEngine assemblyEngine = new AssemblyStepEngine();
        AssemblyStepBody assemblyBody= new AssemblyStepBody();

        AssemblyLine assemblyLine = new AssemblyLine(assemblyChassis, assemblyEngine, assemblyBody);
        IProduct finishedCar = assemblyLine.assembleProduct(new Car());
    }
}
