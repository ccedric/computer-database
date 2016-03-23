package com.excilys.formation.java.computerdb.dto;

import com.excilys.formation.java.computerdb.validator.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data Transfer Object of the model Computer.
 * 
 * @author Cédric Cousseran
 *
 */
public class ComputerDto {
  private long id;
  @Size(min = 2, max = 50)
  @NotNull
  private String name;
  private String companyName;
  private long companyId;
  @Date
  private String introduced;
  @Date
  private String discontinued;

  /**
   * Verify that the discontinued date is after the introduced date.
   */
  @AssertTrue
  public boolean isDiscontinuedAfterIntroduced() {
    if (introduced == null || discontinued == null) {
      return true;
    }
    if (introduced.equals("") && discontinued.equals("")) {
      return true;
    }
    if (introduced.equals("") && !discontinued.equals("")) {
      return false;
    }

    return (discontinued.compareTo(introduced) > 0);
  }

  private ComputerDto(ComputerDtoBuilder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.companyId = builder.companyId;
    this.companyName = builder.companyName;
    this.introduced = builder.introduced;
    this.discontinued = builder.discontinued;
  }

  public ComputerDto() {
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public long getCompanyId() {
    return companyId;
  }

  public void setCompanyId(long companyId) {
    this.companyId = companyId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIntroduced() {
    return introduced;
  }

  public void setIntroduced(String introduced) {
    this.introduced = introduced;
  }

  public String getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(String discontinued) {
    this.discontinued = discontinued;
  }

  public static class ComputerDtoBuilder {
    private long id;
    private String name;
    private long companyId;
    private String companyName;
    private String introduced;
    private String discontinued;

    public ComputerDtoBuilder(String name) {
      this.name = name;
    }

    public ComputerDtoBuilder id(long id) {
      this.id = id;
      return this;
    }

    public ComputerDtoBuilder companyId(long companyId2) {
      this.companyId = companyId2;
      return this;
    }

    public ComputerDtoBuilder companyName(String companyName) {
      this.companyName = companyName;
      return this;
    }

    public ComputerDtoBuilder introduced(String introduced) {
      this.introduced = introduced;
      return this;
    }

    public ComputerDtoBuilder discontinued(String discontinued) {
      this.discontinued = discontinued;
      return this;
    }

    public ComputerDto build() {
      return new ComputerDto(this);
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) companyId;
    result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
    result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
    result = prime * result + (int) id;
    result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ComputerDto other = (ComputerDto) obj;
    if (companyId != other.companyId) {
      return false;
    }
    if (companyName == null) {
      if (other.companyName != null) {
        return false;
      }
    } else if (!companyName.equals(other.companyName)) {
      return false;
    }
    if (discontinued == null) {
      if (other.discontinued != null) {
        return false;
      }
    } else if (!discontinued.equals(other.discontinued)) {
      return false;
    }
    if (id != other.id) {
      return false;
    }
    if (introduced == null) {
      if (other.introduced != null) {
        return false;
      }
    } else if (!introduced.equals(other.introduced)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ComputerDTO [id=" + id + ", name=" + name + ", companyName=" + companyName
        + ", companyId=" + companyId + ", introduced=" + introduced + ", discontinued="
        + discontinued + "]";
  }

}
