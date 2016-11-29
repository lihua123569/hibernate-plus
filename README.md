![app-hibernate-persist-Logo](http://git.oschina.net/uploads/images/2016/1121/010747_31fb5e63_620321.jpeg "logo")

> 为简化开发工作、提高生产率而生

# 简介 | Intro

Hibernate 增强工具包 - 只做增强不做改变，更加精简持久层`CRUD`操作

# 优点 | Advantages

- **纯正血统**：完全继承原生 `Hibernate` 的所有特性
- **最少依赖**：仅仅依赖 `Hibernate`
- **自动生成代码**：简化操作，使其专注于业务
- **自定义操作**：提供大量API，使开发更加顺畅
- **简化操作**：只需专注于业务，查询操作请交给 `Hibernate-Plus`
- **无缝分页**：基于`Hibernate`分页，无需具体实现
- **数据库友好**：基于`Hibernate`，支持目前大多数主流数据库
- **避免Sql注入**：内置对特殊字符转义，从根本上预防Sql注入攻击
- **无配置文件**：无需编写SQL配置文件(例如：`Mybatis`的XML)，从而简化操作
- **主从分离**：简单配置即可实现主从分离

# 应用实例 | Demo

[Spring-MVC](http://git.oschina.net/cancerGit/springmvc-hibernate-plus)

# 结构目录 | Architecture

![项目结构说明](http://git.oschina.net/uploads/images/2016/1126/164418_9ce74358_620321.png "项目结构说明")

# 通用方法 | API

![API说明](http://git.oschina.net/uploads/images/2016/1126/164243_7fc54d36_620321.png "API说明")

#优点?

> Hibernate-Plus都可以极大的方便开发人员。可以随意的按照自己的需要选择通用方法。
>
> 让你感觉使用Hibernate感觉跟Mybatis-Plus一样，极大简化开发。

##Hibernate-Plus - Spring集成
	
 	<!-- 配置Hibernate-Plus SessionFactory -->
    <bean id="sessionFactory" class="com.baomidou.hibernateplus.LocalSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="hibernateProperties">
            <props>
				<!--Hibernate自动建表相关-->
                <!--<prop key="hibernate.hbm2ddl.auto">${hibernate.hbm2ddl.auto}</prop>-->
				<!--数据库方言-->
                <prop key="hibernate.dialect">${hibernate.dialect}</prop>
                <prop key="hibernate.show_sql">${hibernate.show_sql}</prop>
                <prop key="hibernate.format_sql">${hibernate.format_sql}</prop>
                <prop key="hibernate.use_sql_comments">${hibernate.use_sql_comments}</prop>
            </props>
        </property>

        <!-- 自动扫描注解方式配置的Hibernate-Plus映射类文件 -->
        <property name="packagesToScan">
            <list>
                <value>com.baomidou.hibernate.model.po</value>
            </list>
        </property>

    </bean>
	 <!-- 配置事务管理器 -->
    <bean name="transactionManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
        <property name="sessionFactory" ref="sessionFactory"></property>
    </bean>
    <!-- 拦截器方式配置事物 -->
    <tx:advice id="transactionAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="save*" propagation="REQUIRED"/>
            <tx:method name="update*" propagation="REQUIRED"/>
            <tx:method name="delete*" propagation="REQUIRED"/>
            <tx:method name="get*" propagation="REQUIRED" read-only="true"/>
            <tx:method name="select*" propagation="REQUIRED" read-only="true"/>
            <tx:method name="query*" propagation="REQUIRED" read-only="true"/>
            <tx:method name="*" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>
    <aop:config>
        <aop:pointcut id="transactionPointcut"
                      expression="execution(* com.baomidou.hibernate.service..*Impl.*(..)) or execution(* com.baomidou.framework.service..*Impl.*(..))"/>
        <aop:advisor pointcut-ref="transactionPointcut" advice-ref="transactionAdvice"/>
    </aop:config>	

###DAO层

	public interface DemoDao extends IDao<Tdemo, Vdemo> {
	
	}

	@Repository
	public class DemoDaoImpl extends DaoImpl<Tdemo, Vdemo> implements DemoDao {
	
	}

###Service层

	public interface DemoService extends IService<Vdemo> {

	}

	@Service
	public class DemoServiceImpl extends ServiceImpl<Tdemo, Vdemo> implements DemoService {
	
	}

###实体类注解
	    @Entity
		@Table(name = "demo")
		@DynamicInsert(true)
		@DynamicUpdate(true)
		public class Tdemo extends AutoPrimaryKey {
	
			private String demo1;
			private String demo2;
			private String demo3;
		
			@Column(name = "demo1")
			public String getDemo1() {
				return demo1;
			}
		
			public void setDemo1(String demo1) {
				this.demo1 = demo1;
			}
		
			@Column(name = "demo2")
			public String getDemo2() {
				return demo2;
			}
		
			public void setDemo2(String demo2) {
				this.demo2 = demo2;
			}
		
			@Column(name = "demo3")
			public String getDemo3() {
				return demo3;
			}
		
			public void setDemo3(String demo3) {
				this.demo3 = demo3;
			}
		}

####注解支持

>完全支持 `JPA` 注解，原生 `Hibernate` 用法

####强调

>不是表中字段的属性需要添加`@Transient`注解  

####示例项目
>http://git.oschina.net/cancerGit/springmvc-hibernate-plus

##Hibernate-Plus - 简单用法示例

全部针对单表操作，每个实体类都需要继承Convert来PO、TO之间的装换

示例代码：

	//查询表中所有数量
	int count = demoService.selectCount();
    //根据条件查询数量 Condition的链式风格使你的代码看起来更加优美
	int count = demoService.selectCount(Condition.instance().eq("id",1));
	//查询单条数据
	Demo demo = demoService.selectOne(Condition.instance().eq("id",1));
	List<Demo> demos = demoService.selectOne(Condition.instance().gt("id",1).like("name","plus").in("sex","1,2,3"));
    //查询分页
    Page<Demo> demos = demoService.selectPage(new Page(1,10));
    下面是API自行感受
	...

###Service通用API

	public V get(Serializable id);
	public V get(String property, Object value);
	public V save(V vo);
	public void saveOrUpdate(V vo);
	public void update(V vo);
	public boolean update(Map<String, Object> setMap, Wrapper wrapper);
	public void delete(V vo);
	public boolean delete(Wrapper wrapper);
	public boolean insertBatch(List<V> list);
	public boolean insertBatch(List<V> list, int size);
	public boolean updateBatch(List<V> list);
	public boolean updateBatch(List<V> list, int size);
	public V selectOne(Wrapper wrapper);
	public List<V> selectList(Wrapper wrapper);
	public List<Map<String, Object>> selectMaps(Wrapper wrapper);
	public int selectCount();
	public int selectCount(String property, Object... value);
	public int selectCount(String[] property, Object... value);
	public int selectCount(Map<String, Object> map);
	public int selectCount(Wrapper wrapper);
	public Page<V> selectPage(Page<V> page);
	public Page<V> selectPage(Page<V> page, String property, Object value);
	public Page<V> selectPage(Wrapper wrapper, Page<V> page);
	public Page<Map<String, Object>> selectMapPage(Wrapper wrapper, Page<Map<String, Object>> page);

###Dao通用API

	public T get(Serializable id);
	public T get(String property, Object value);
	protected T get(String hql);
	protected T get(String hql, Map<String, Object> params);
	public T save(T t);
	public void saveOrUpdate(T t);
	public void update(T t);
	public int update(Map<String, Object> setMap, Wrapper wrapper);
	public void delete(T t);
	public int delete(Wrapper wrapper);
	public boolean insertBatch(List<T> list, int size);
	public boolean updateBatch(List<T> list, int size);
	public <T> List<T> selectList(Wrapper wrapper);
	public List<Map<String, Object>> selectMaps(Wrapper wrapper);
	public int selectCount();
	public int selectCount(String property, Object... value);
	public int selectCount(String[] property, Object... value);
	public int selectCount(Map<String, Object> params);
	public int selectCount(Wrapper wrapper);
	public Page<Map<String, Object>> selectMapPage(Wrapper wrapper, Page<Map<String, Object>> page);
	public Page selectPage(Wrapper wrapper, Page page);
	public List<T> query(String property, Object value);
	public List<T> query(String[] property, Object... value);
	public List<T> query(int page, int rows, String property, Object value);
	public List<T> query(int page, int rows, String[] property, Object... value);
	public List<T> query(String order, String property, Object value);
	public List<T> query(String order, String[] property, Object... value);
	public List<T> query(int page, int rows, String order, String property, Object value);
	public List<T> query(int page, int rows, String order, String[] property, Object... value);
	public List<T> query(String order);
	public List<T> query(String order, int page, int rows);
	public List<T> query();
	public List<T> query(int page, int rows);
	public List<T> query(Map<String, Object> params);
	public List<T> query(Map<String, Object> params, String order);
	public List<T> query(int page, int rows, Map<String, Object> params, String order);
	public List<T> query(int page, int rows, Map<String, Object> map);
	protected int executeHql(String hql);
	protected int executeHql(String hql, Map<String, Object> params);
	protected List<T> queryListWithHql(String hql);
	protected List<T> queryListWithHql(String hql, Map<String, Object> params);
	protected List<T> queryListWithHql(String hql, Map<String, Object> params, int page, int rows);
	protected List<T> queryListWithHql(String hql, int page, int rows);
	protected List<Map<String, Object>> queryMapsWithHql(String hql);
	protected List<Map<String, Object>> queryMapsWithHql(String hql, int page, int rows);
	protected int queryCountWithHql(String hql);
	protected int queryCountWithHql(String hql, Map<String, Object> params);
	protected int executeSqlUpdate(String sql);
	protected int executeSqlUpdate(String sql, Object[] args);
	protected int executeSqlUpdate(String sql, Map<String, Object> params);
	protected int queryCountWithSql(String sql);
	protected int queryCountWithSql(String sql, Map<String, Object> params);
	protected Map<String, Object> queryMapWithSql(String sql, Map<String, Object> params);
	protected Map<String, Object> queryMapWithSql(String sql);
	protected List<Map<String, Object>> queryMapsWithSql(String sql, Object[] args);
	protected List<Map<String, Object>> queryMapsWithSql(String sql);
	protected List<Map<String, Object>> queryMapsWithSql(String sql, int page, int rows);
	protected List<Map<String, Object>> queryMapsWithSql(String sql, Map<String, Object> params, int page, int rows)
	protected List<Map<String, Object>> queryMapsWithSql(String sql, Map<String, Object> params);
	protected List queryListWithSql(String sql, Object[] args);
	protected List queryListWithSql(String sql);
	protected List queryListWithSql(String sql, int page, int rows);
	protected List queryListWithSql(String sql, Map<String, Object> params, int page, int rows);
	protected List queryListWithSql(String sql, Map<String, Object> params);
	protected Map<String, Object> queryMapWithSql(String sql, Object[] args);
	public List<Map<String, Object>> queryMapsWithClass(String property, Object value);
	public List<Map<String, Object>> queryMapsWithClass(String[] property, Object... value);
	public List<Map<String, Object>> queryMapWithClass(Map<String, Object> map);


	

# 其他开源项目 | Other Project

     `如果你喜欢Mybatis，可以尝试使用`
- [Mybatis-Plus](http://git.oschina.net/baomidou/mybatis-plus)

# 期望 | Futures

> 欢迎提出更好的意见，帮助完善 
