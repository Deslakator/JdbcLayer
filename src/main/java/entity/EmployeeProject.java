package entity;

public class EmployeeProject {

    private long employeeId;
    private long projectId;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeProject that = (EmployeeProject) o;

        if (employeeId != that.employeeId) return false;
        return projectId == that.projectId;
    }

    @Override
    public int hashCode() {
        int result = (int) (employeeId ^ (employeeId >>> 32));
        result = 31 * result + (int) (projectId ^ (projectId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "EmployeeProject{" +
                "employeeId=" + employeeId +
                ", projectId=" + projectId +
                '}';
    }
}
