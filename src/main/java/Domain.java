import database.Util;
import entity.Address;
import entity.Employee;
import entity.EmployeeProject;
import entity.Project;
import service.AddressService;
import service.EmployeeProjectService;
import service.EmployeeService;
import service.ProjectService;

public class Domain {

    public static void main(String[] args) {
        Util util = new Util();
        //util.createTable();

        AddressService addressService = new AddressService(util);
        EmployeeService employeeService = new EmployeeService(util);
        EmployeeProjectService employeeProjectService = new EmployeeProjectService(util);
        ProjectService projectService = new ProjectService(util);

        Domain domain = new Domain(addressService, employeeService, employeeProjectService, projectService);

        domain.writeObjectsToDB();
        domain.readAllObject();
    }

    private final AddressService addressService;
    private final EmployeeService employeeService;
    private final EmployeeProjectService employeeProjectService;
    private final ProjectService projectService;

    public Domain(AddressService addressService, EmployeeService employeeService, EmployeeProjectService employeeProjectService, ProjectService projectService) {
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.employeeProjectService = employeeProjectService;
        this.projectService = projectService;
    }

    private void writeObjectsToDB() {
        Address address = new Address();
        address.setCity("Москва");
        address.setPostcode("122549");
        addressService.add(address);

        Employee employee = new Employee();
        employee.setName("Daniil");
        employee.setAddressId(address.getId());
        employeeService.add(employee);

        Project project = new Project();
        project.setTitle("JDBC project");
        projectService.add(project);

        EmployeeProject employeeProject = new EmployeeProject();
        employeeProject.setEmployeeId(employee.getId());
        employeeProject.setProjectId(project.getId());
        employeeProjectService.add(employeeProject);
    }

    private void readAllObject() {
        addressService.getAll().forEach(System.out::println);
        employeeService.getAll().forEach(System.out::println);
        projectService.getAll().forEach(System.out::println);
        employeeProjectService.getAll().forEach(System.out::println);
    }
}
