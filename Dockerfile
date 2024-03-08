#######################
# Dockerfile的用途
# 用于打docker镜像，把shop商城的war包，打进tomcat:8.5.84-jdk8基础镜像中，产出自己的docker镜像。
#
# 打包方法：
# 直接使用原生的docker命令打包。
# 1、先使用maven构建出war包，这是一步常规操作，与Docker无关。
# 2、进入项目的根目录执行命令：docker build -t shop:v2 .
# 3、注意命令的最后有一个点，shop:v2 是镜像的名称请你自行修改。
#
# 特点：
# 不使用 docker-maven-plugin 或 dockerfile-maven-plugin 这种依赖maven的插件。
# 而是直接使用原生的docker命令打包，适用于本项的Maven多模块的工程结构，让事情简化。
#######################

# 基础镜像使用tomcat:8.5.84-jdk8
#https://hub.docker.com/layers/library/tomcat/8.5.84-jdk8/images/sha256-4db38a3cb30d5c327d68c629e12fc1e3e06e2a41b148f275952b2a4de759b43f?context=explore
FROM tomcat:8.5.84-jdk8

#Dockerfile 中创建时区文件，固定使用+0800 东8时间 上海时间
#在构建基础镜像或在基础镜像的基础上制作自定义镜像时，在 Dockerfile 中创建时区文件即可解决单一容器内时区不一致问题，且后续使用该镜像时，将不再受时区问题困扰。
#Docker容器系统时间默认为 UTC 协调世界时间 （Universal Time Coordinated），与节点本地所属时区 CST （上海时间）相差8个小时。
#若不创建时区以下的文件，单一容器内的时区将是默认+0000 时区，会差8个小时。
RUN rm -f /etc/localtime \
&& ln -sv /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
&& echo "Asia/Shanghai" > /etc/timezone


# 将war包copy到tomcat的合适目录中（适用于Jenkins自动化环境）
COPY *.war /usr/local/tomcat/webapps/

# 将war包copy到tomcat的合适目录中（适用用windows开发环境）
# COPY shop-web-admin/target/*.war /usr/local/tomcat/webapps/
# COPY shop-web-front/target/*.war /usr/local/tomcat/webapps/
# COPY shop-web-static/target/*.war /usr/local/tomcat/webapps/
# COPY shop-web-upload/target/*.war /usr/local/tomcat/webapps/
# COPY shop-web-wap/target/*.war /usr/local/tomcat/webapps/

# 占用容器内的端口
# 令是声明运行时容器提供服务端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务。
EXPOSE 8080