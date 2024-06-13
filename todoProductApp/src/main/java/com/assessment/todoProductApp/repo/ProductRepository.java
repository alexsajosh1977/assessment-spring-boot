package com.assessment.todoProductApp.repo;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.assessment.todoProductApp.exceptions.DatabaseOperationFailedException;
import com.assessment.todoProductApp.models.Product;
import com.assessment.todoProductApp.models.ProductMapper;

@Repository
@Component
public class ProductRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String SQL_FIND_PRODUCT = "select * from product where id = ?";
	private final String SQL_DELETE_PRODUCT = "delete from product where id = ?";
	private final String SQL_UPDATE_PRODUCT = "update product set name= ?, status = ? where id = ?";
	private final String SQL_GET_ALL = "select * from product";
	private final String SQL_INSERT_PRODUCT = "insert into product(id, name, status) values(?,?,?)";


	public Number createProduct(Product product) {
		
	    GeneratedKeyHolder holder = new GeneratedKeyHolder();	    
	    
	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection.prepareStatement(SQL_INSERT_PRODUCT, 
	                               Statement.RETURN_GENERATED_KEYS);
	        ps.setString(2, product.getName());
            ps.setString(3, product.getStatus());

	        return ps;
	    }, holder);	  
	    
	    if(holder.getKey() == null )
	    	throw new DatabaseOperationFailedException("Product creation failed ");
	    
	    return holder.getKey();
	}
	
	public Product getProductById(Long id) {
		
		return jdbcTemplate.queryForObject(SQL_FIND_PRODUCT, new ProductMapper(), id);
	}

	public List<Product> getAllProducts() {
		return jdbcTemplate.query(SQL_GET_ALL, new ProductMapper());
	}

	public boolean deleteProduct(Long productId) {
		return jdbcTemplate.update(SQL_DELETE_PRODUCT, productId) > 0;
	}

	public boolean updateProduct(Product product) {
	
		 return jdbcTemplate.update(SQL_UPDATE_PRODUCT, product.getName(), product.getStatus(), product.getId()) > 0;
		
	
	}

}
