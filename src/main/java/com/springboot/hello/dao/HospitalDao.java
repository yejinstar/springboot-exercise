package com.springboot.hello.dao;

import com.springboot.hello.domain.Hospital;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class HospitalDao {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public HospitalDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    public void add(Hospital hospital) {
        String sql = "INSERT INTO `likelion-db`.`nation_wide_hospitals` (`id`, `open_service_name`, `open_local_government_code`, `management_number`, `license_date`, `business_status`, `business_status_code`, `phone`, `full_address`, `road_name_address`, `hospital_name`, `business_type_name`, `healthcare_provider_count`, `patient_room_count`, `total_number_of_beds`, `total_area_size`)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);"; // 16개
        this.jdbcTemplate.update(sql,
                hospital.getId(), hospital.getOpenServiceName(), hospital.getOpenLocalGovernmentCode(),
                hospital.getManagementNumber(), hospital.getLicenseDate(), hospital.getBusinessStatus(),
                hospital.getBusinessStatusCode(), hospital.getPhone(), hospital.getFullAddress(),
                hospital.getRoadNameAddress(), hospital.getHospitalName(), hospital.getBusinessTypeName(),
                hospital.getHealthcareProviderCount(), hospital.getPatientRoomCount(), hospital.getTotalNumberOfBeds(),
                hospital.getTotalAreaSize()
        );

        /*this.jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                                ps.setInt(1,hospital.getId());
                                ps.setString(2,hospital.getOpenServiceName());
                                ps.setInt(3,hospital.getOpenLocalGovernmentCode());
                                ps.setString(4,hospital.getManagementNumber());
                                ps.setString(5, String.valueOf(hospital.getLicenseDate()));
                                ps.setInt(6,hospital.getBusinessStatus());
                                ps.setInt(7,hospital.getBusinessStatusCode());
                                ps.setString(8,hospital.getPhone());
                                ps.setString(9,hospital.getFullAddress());
                                ps.setString(10,hospital.getRoadNameAddress());
                                ps.setString(11,hospital.getHospitalName());
                                ps.setString(12,hospital.getBusinessTypeName());
                                ps.setInt(13,hospital.getHealthcareProviderCount());
                                ps.setInt(14,hospital.getPatientRoomCount());
                                ps.setInt(15,hospital.getTotalNumberOfBeds());
                                ps.setFloat(16,hospital.getTotalAreaSize());
                    }
                    @Override
                    public int getBatchSize() {
                        return 0;
                    }
                }
        );*/
    }
    RowMapper<Hospital> rowMapper = (rs, rowNum) -> {
        Hospital hospital = new Hospital();
        hospital.setId(rs.getInt("id"));
        hospital.setOpenServiceName(rs.getString("open_service_name"));
        hospital.setManagementNumber(rs.getString("management_number"));
        hospital.setOpenLocalGovernmentCode(rs.getInt("open_local_government_code"));
        hospital.setLicenseDate(rs.getTimestamp("license_date").toLocalDateTime());
        hospital.setBusinessStatus(rs.getInt("business_status"));
        hospital.setBusinessStatusCode(rs.getInt("business_status_code"));
        hospital.setPhone(rs.getString("phone"));
        hospital.setFullAddress(rs.getString("full_address"));
        hospital.setRoadNameAddress(rs.getString("road_name_address"));
        hospital.setHospitalName(rs.getString("hospital_name"));
        hospital.setBusinessTypeName(rs.getString("business_type_name"));
        hospital.setHealthcareProviderCount(rs.getInt("healthcare_provider_count"));
        hospital.setPatientRoomCount(rs.getInt("patient_room_count"));
        hospital.setTotalNumberOfBeds(rs.getInt("total_number_of_beds"));
        hospital.setTotalAreaSize(rs.getFloat("total_area_size"));

        return hospital;
    };

    public Hospital findById(int id){
        return this.jdbcTemplate.queryForObject("SELECT * FROM nation_wide_hospitals " +
                "WHERE id = ?", rowMapper, id);
    }

    public void deleteAll(){
        this.jdbcTemplate.update("DELETE FROM nation_wide_hospitals");
    }

    public int getCount(){
        String sql = "SELECT COUNT(id) FROM nation_wide_hospitals;";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

}
