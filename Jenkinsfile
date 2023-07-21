///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Jenkinsfile文件说明：
//  本文件是Jenkins流水线文件，Jenkins服务会从git中检出本文件，并运行流水线，做全流程的构建工作。
//
//参考文章：
//  Jenkinsfile 详解 https://www.cnblogs.com/-ori/p/16924265.html
//  jenkins流水线详解，保姆式教程:https://blog.csdn.net/qq_57581439/article/details/126281020
//  Jenkins打包微服务构建Docker镜像运行:https://blog.51cto.com/u_15899048/5903512
//  SonarQube 9.x与Jenkins进行集成并扫描后端java以及前端vue代码:https://blog.csdn.net/qq_44930876/article/details/128147777
//  Jenkins：常用指令说明 https://www.bloghome.com.cn/post/jenkins-chang-yong-zhi-ling-shuo-ming-san.html
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

pipeline {
    agent any

    tools{
        maven 'maven3.8.4'  // 这里导入maven，名字'maven3.8.4'就是在Jenkins“全局工作配置”中配置的名称，一定要对应上。
        jdk "openjdk8"  // 这里导入jdk，名字"openjdk8"就是在Jenkins“全局工作配置”中配置的名称，一定要对应上。
    }

    //定义一批全局变量
    environment {
        //gitlab代码仓库的URL,将从这里拉取工程的源代码。
        GitLab_Registry_URL= 'http://192.168.92.42/root/b2b2cShop.git'
        GitLabURL4MySQL= 'http://192.168.92.42/docker/docker-mysql.git'

        //SonqrQube中配置的项目信息
        SonarQube_projectKey= 'B2b2cShop'
        SonarQube_URL= 'http://192.168.92.42:9091'
        SonarQube_Secret= 'e15bdbca31f43b8a592d1e445e4fc4268ae3911f'

        //nexus3仓库地址,打好的Dcoker镜像包，将push上传到这个仓库。
        nexus_Registry_URL= 'nexus.sicheng.net:8083'
        nexus_Registry_Cert= 'xxxx'

        //定义环境变量--fdp.properties配置文件路径(已根据环境正常选择了对应的文件)
        fdpConfigFilePath="shop-data/src/main/resources/${env.deploy_env}/fdp.properties"

//         //为什么注释了？答:不需要了，改策略了，不需要从pom.xml中提取版本号了，改为从fdp.p文件中提取版本号了，见：stage('生成版本号')。jenkins构建时会读取fdp.p配置文件中的version=2.0.x版本号，关自动把x替换为编译号，用于Docker镜像的命名加入版本号。
//         //用于从pom.xml中提取出版本号(groupId\artifactId\version)
//         //Jenkins要先安装pipeline-utility-steps插件。
//         PomGroupId = readMavenPom().getGroupId()
//         PomArtifactId = readMavenPom().getArtifactId()
//         PomVersion = readMavenPom().getVersion()

        // 中文商城的MySQL的账号，若使用env-cn环境，将从此库导出shop.sql全量数据文件，用于创建Mysql的Docker镜像将包含这个数据，第一次启动容器时会完成数据导入。
        cn_mysql_ip= '192.168.92.30'
        cn_mysql_port='3306'
        cn_mysql_user='root'
        cn_mysql_pass='123456'
        cn_mysql_dbname='shop-cn-2'

        // 英文商城的MySQL的账号，若使用env-cn环境，将从此库导出shop.sql全量数据文件，用于创建Mysql的Docker镜像将包含这个数据，第一次启动容器时会完成数据导入。
        en_mysql_ip= '192.168.92.30'
        en_mysql_port='3306'
        en_mysql_user='root'
        en_mysql_pass='123456'
        en_mysql_dbname='shop-en-3'

        // 定义Mysql的Docker镜像的名称，打包时将使用这个名称
        imageMysql='mysql56'

        //定义环境变量--Docker环境MySQL的信息，用容器来运行全套shop程序时要使用此信息
        docker_mysql_ip='172.28.0.105'
        docker_mysql_port='3306'
        docker_mysql_user='root'
        docker_mysql_password='123456'
        docker_mysql_dbname_cn='shop-cn-2'
        docker_mysql_dbname_en='shop-en-3'

        //定义环境变量--Docker环境redisL的信息，用容器来运行全套shop程序时要使用此信息
        docker_redis_ip='172.28.0.100'
        docker_redis_port='6379'
        docker_redis_password='123456'

        //定义环境变量--Docker环境Solr的信息，用容器来运行全套shop程序时要使用此信息
        docker_solr_ip='172.28.0.101'
        docker_solr_port='8983'

        //定义环境变量--Docker环境Shop主程序的信息，用容器来运行全套shop程序时要使用此信息
        docker_shop_ip='172.28.0.10'
        docker_shop_port='80'
    }

    //Jenkins 参数化构建
    parameters {
        gitParameter branch: '', branchFilter: 'origin/(.*)', defaultValue: 'origin/main', description: '【Branch变量】请选择一个分支来拉取代码。默认值：main；99%你应选择main主干', name: 'Branch', quickFilterEnabled: false, selectedValue: 'NONE', sortMode: 'ASCENDING_SMART', tagFilter: '*', type: 'GitParameterDefinition', listSize:'15'
        choice(choices: ['env-cn','env-en'], description: '【deploy_env变量】请选择环境，env-cn是中文商城，env-en是英文商城，每个环境有自己的fdp.properties配置文件。默认值是第一个选项；', name: 'deploy_env')

        booleanParam  description: '【JUnit变量】是否执行JUnit单元测试+覆盖率分析，默认否；', name: 'JUnit' ,defaultValue: true
        booleanParam  description: '【CodeQualityInspection变量】是否执行SonarQube代码质量检查，默认否；执行需要约10分钟。', name: 'CodeQualityInspection' ,defaultValue: true

        booleanParam  description: '【CreateImage变量】是否创建Docker镜像Push到Nexus3仓库，默认是；', name: 'CreateImage' ,defaultValue: true
        string(name: 'dockerImageName', defaultValue: 'b2b2c-shop', description: '【dockerImageName变量】Docker 镜像的名称，默认值b2b2c-shop，不要轻易修改。',trim: true)
//        string(name: 'dockerImageVersion', defaultValue: '2.0.1', description: '【dockerImageVersion变量】Docker 镜像的的版本，默认值2.0.1',trim: true)
//        booleanParam description: '【PushImage2Nexus变量】是否将本次构建镜像推送到Nexus3仓库，默认是；', name: 'PushImage2Nexus' ,defaultValue: true
//        booleanParam description: '【PushImage2Registry变量】是否将本次构建镜像推送到Docker Hub库，默认否；', name: 'PushImage2Registry' ,defaultValue: false
//        choice(name:'Replicase', choices:'1\n3\n5', description:'请选择副本数(如果对此参数不清楚的话，默认即可);' )
    }

    stages {
        stage('拉取Shop代码') {
            steps {
                echo '####################################'
                echo "#### git拉取Shop代码 从：${env.GitLab_Registry_URL}"
                echo '####################################'
                //credentialsId 是Jenkins中配置的凭证ID，用于访问GitLab
                //变量 env.GitLab_Registry_URL ，是gitlab代码仓库的URL
                git branch: 'main', credentialsId: 'c00cb58c-1457-4703-835d-9198ffa5cc0a', url: env.GitLab_Registry_URL
                echo '####################################'
                echo '#### 拉取Shop代码 完成'
                echo '####################################'
            }
        }

        stage('生成版本号') {
            steps {
                //以下取环境变量的方法都是正确的，放在这里供你参考
//                echo env.BUILD_TAG
//                echo "PomGroupId: ${env.PomGroupId}"
//                echo "PomArtifactId: ${env.PomArtifactId}"
//                echo "PomVersion: ${PomVersion}"
                echo '####################################'
                echo "#### 显示版本号 ####"
                echo '####################################'
                echo "#### BUILD_TAG: ${env.BUILD_TAG}"

                script{
                    echo "#### 从fdp.p配置文件中提取版本号 'version = 2.0.x' 中的 '2.0' 部分，赋值给PomVersion变量"
                    PomVersion= sh(returnStdout: true, script: "sed -n 's/^version\\s*=\\s*\\([0-9]*\\.[0-9]*\\)\\..*/\\1/p' ${fdpConfigFilePath}")
                    PomVersion=PomVersion.trim().replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");//去空格去回车
                    echo "#### 结果，PomVersion = ${PomVersion}"
                }

                echo "#### 最终使用的版本号是: ${PomVersion}.${BUILD_NUMBER}，（添加了构建编号）"
                echo "#### 把版本号${PomVersion}.${BUILD_NUMBER}写入到fdp.p配置文件中"
                sh "sed -i 's#^version\\s*=.*#version=${PomVersion}.${BUILD_NUMBER}#g' ${fdpConfigFilePath}"
            }
        }

        stage('单元测+覆盖率') {
            //【JUnit变量】是否执行JUnit单元测试+覆盖率分析
            //为什么 '单元测+覆盖率' 要独立成一个stage步骤？
            //答：运行单元测试需要依赖对应的环境，环境中要有msyql\redis\solr才能成功运行JUnit单元测试。要使用对应环境的fdp.p文件，不可使用被替换过的fdp.p文件。
            when { expression { return params.JUnit } }
            steps {
                script {
                    echo '####################################'
                    echo "#### 执行JUnit单元测试，生成单元测试报告。 "
                    echo "#### 执行JaCoCo代码覆盖率分析，生成代码覆盖率报告。"
                    echo '####################################'
                    //使用-U参数： 该参数能强制让Maven检查所有SNAPSHOT依赖更新，确保集成基于最新的状态，如果没有该参数，Maven默认以天为单位检查更新，而持续集成的频率应该比这高很多。
                    //"-Dmaven.test.failure.ignore=true" 建议加上，否则如果单元测试失败，就会直接中断，不会产生 .exec 文件。(已在pom.xml中加了<testFailureIgnore>true</testFailureIgnore>)
                    sh "mvn clean compile test -U -P${env.deploy_env}"
                }
                echo '####################################'
                echo '#### 执行JUnit单元测试+覆盖率分析 完成'
                echo '####################################'

                echo '####################################'
                echo '#### 生成汇总性质单元测试报告'
                echo '####################################'
                //xUnit 插件可生成汇总性质单元测试报告，需要先给jenkins安装xUnit的插件，安装完成后，在流水线语法中就有了xUnit: Publish xUnit test result reports。
                xunit checksName: '', tools: [JUnit(excludesPattern: '', pattern: '*/target/surefire-reports/*.xml', stopProcessingIfError: true)]

                echo '####################################'
                echo '#### 在“任务面板”添加覆盖率报告入口'
                echo '####################################'
                //在“任务面板”添加覆盖率报告入口–流水线Pipeline方式
                //这个publishHTML命令不创建任何html内容的，它只把已有的html内容加入到Jnekins的“任务面板”上。
                //使用流水线的publishHTML命令。我们使用流水线语法工具生成一个PublishHTML命令。
                //之前的配置中 <outputDirectory>target/jacoco-site</outputDirectory>  指定了输出目录：shop-junit-jacoco/target/jacoco-site
                publishHTML([
                    allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false,
                    reportDir: 'shop-junit-jacoco/target/jacoco-site',
                    reportFiles: 'index.html',
                    reportName: '代码覆盖率报告', reportTitles: '', useWrapperFileDirectly: true])
            }
        }

        stage('Sonar代码质量') {
            //【CodeQualityInspection变量】是否执行SonarQube代码质量检查；
            when { expression { return params.CodeQualityInspection } }
            steps {
                echo '####################################'
                echo '#### Sonar代码质量检查'
                echo '####################################'
                //问：下面的'SonarQube'是哪来的？
                //答：是在jenkins手工配置的sonarqube服务器信息，jenkins需要先安装下载"SonarQube Scanner for jenkins"插件
                //位置：【系统管理】➡【系统配置Configure System】➡【SonarQube servers】
                //SonarQube installations的Name是'SonarQube'，通过此Name可找到SonarQube服务器？
                withSonarQubeEnv('SonarQube') {
                    sh """
                      mvn sonar:sonar \
                      -Dsonar.projectKey=${SonarQube_projectKey} \
                      -Dsonar.host.url=${SonarQube_URL} \
                      -Dsonar.login=${SonarQube_Secret} \
                      -Dsonar.projectVersion=${PomVersion}.${BUILD_NUMBER}
                    """
                }
                echo '####################################'
                echo '#### Sonar代码质量检查 完成'
                echo '####################################'
            }
        }
        stage('编译打包') {
            steps {
                // mvn --version 先查询一下maven版本，用于确认maven是否安装好
                // sh 是指 执行shell脚本
                echo '####################################'
                echo '#### 输出一下maven工具的版本，用于确认maven是否已安装好：'
                echo '####################################'
                sh "mvn --version"

                echo '####################################'
                echo '#### 替换配置文件中的账号信息用以适应未来的Docker环境，如MySQL的ip、端口、账号、密码，Rdis的ip、端口、账号、密码，搜索引擎的URL等等；'
                echo "#### 基础配置文件是：${fdpConfigFilePath}，对此文件进行字符替换。"
                echo '####################################'
                sh """
                    echo '#### 替换mysql的用户名、密码、ip、端口、数据库名为将来Docker环境中和信息'
                    sed -i "s#^jdbc.username\\s*=.*#jdbc.username=${docker_mysql_user}#g" ${fdpConfigFilePath}
                    sed -i "s#^jdbc.password\\s*=.*#jdbc.password=${docker_mysql_password}#g" ${fdpConfigFilePath}
                    #原URL：jdbc.url=jdbc:mysql://192.168.92.30:3306/shop2?usxxxxxx参数
                    #sed -i -r "s#^jdbc.url=jdbc:mysql://([0-9\\.]+):([0-9]+)/(.+)\\?(.*)#jdbc.url=jdbc:mysql://ip:端口/库名?\4#" 文件名
                    if [ "${env.deploy_env}" = "env-cn" ]
                    then
                        # env-cn环境，替换为适合env-cn的数据源
                        sed -i -r "s#^jdbc.url\\s*=\\s*jdbc:mysql://([0-9\\.]+):([0-9]+)/(.+)\\?(.*)#jdbc.url=jdbc:mysql://${docker_mysql_ip}:${docker_mysql_port}/${docker_mysql_dbname_cn}?\4#" ${fdpConfigFilePath}
                    else
                        # env-en环境，替换为适合env-en的数据源
                        sed -i -r "s#^jdbc.url\\s*=\\s*jdbc:mysql://([0-9\\.]+):([0-9]+)/(.+)\\?(.*)#jdbc.url=jdbc:mysql://${docker_mysql_ip}:${docker_mysql_port}/${docker_mysql_dbname_en}?\4#" ${fdpConfigFilePath}
                    fi

                    echo '#### 替换redis信息为将来Docker环境中的信息'
                    sed -i "s#^redis.host\\s*=.*#redis.host=${docker_redis_ip}#g" ${fdpConfigFilePath}
                    sed -i "s#^redis.port\\s*=.*#redis.port=${docker_redis_port}#g" ${fdpConfigFilePath}
                    sed -i "s#^redis.password\\s*=.*#redis.password=${docker_redis_password}#g" ${fdpConfigFilePath}

                    echo '#### 替换solr信息为将来Docker环境中的信息'
                    sed -i "s#^\\#solr.url\\s*=.*#solr.url=http://${docker_solr_ip}:${docker_solr_port}/solr/#g" ${fdpConfigFilePath}
                """
                echo '####################################'
                echo "#### Maven开始编译工程 ####"
                echo '####################################'
                //使用-U参数： 该参数能强制让Maven检查所有SNAPSHOT依赖更新，确保集成基于最新的状态，如果没有该参数，Maven默认以天为单位检查更新，而持续集成的频率应该比这高很多。
                //-Dmaven.test.skip=true 是跳过了单元测试，因为前已单独执行过了单元测试了。
                sh "mvn clean package -U '-Dmaven.test.skip=true' -P${env.deploy_env}"
                echo '####################################'
                echo '#### Maven编译、打包 完成'
                echo '####################################'
            }
        }

        stage('创建shop镜像') {
            //【CreateImage变量】是否创建Docker镜像；
            when { expression { return params.CreateImage } }
            steps {
                //问：[uri: 'tcp://192.168.92.44:2375'] 是哪来的？ 答：它是一个远程的Docker引擎，连接上它对它下达命令。
                echo '####################################'
                echo '#### 查看DOCKER_HOST的值'
                echo '####################################'
                echo "DOCKER_HOST: ${env.DOCKER_HOST}"

                // 切换到docker-workspace目录,这里放将要被打包的文件，不参加打包的文件不放进来，文件都要网络传输到docler打包引擎
                dir('docker-workspace') {
                    withDockerServer([uri: 'tcp://192.168.92.44:2375', credentialsId: '']) {
                        sh """#!/bin/bash
                            echo '####################################'
                            echo "#### 当前工作目录在："
                            echo '####################################'
                            pwd

                            echo '####################################'
                            echo "#### copy将要被打包的文件到当前目录 "
                            echo '####################################'
                            cp ../shop-web-admin/target/*.war .
                            cp ../shop-web-front/target/*.war .
                            cp ../shop-web-static/target/*.war .
                            cp ../shop-web-upload/target/*.war .
                            cp ../shop-web-wap/target/*.war .
                            cp ../Dockerfile .
                            mv front.war ROOT.war #改名，ROOT可用/来访问，做为首页入口

                            echo '####################################'
                            echo "#### 工作目录的内容："
                            echo '####################################'
                            ls -a

                            echo '####################################'
                            echo "#### 创建shop镜像"
                            echo '####################################'
                            # 【dockerImageName是参数化构建变量】Docker 镜像的名称，默认值b2b2c-shop
                            # \$BUILD_NUMBER变量是jenkins自身提供的变量，是构建次数。
                            # 注意最后有一个. 表示当前目录
                            docker build -t ${dockerImageName}:${PomVersion}.${BUILD_NUMBER} .

                            echo '####################################'
                            echo "#### 查看镜像列表：docker images"
                            echo '####################################'
                            docker images

                            echo '####################################'
                            echo "#### 登录到 ${env.nexus_Registry_URL}："
                            echo '####################################'
                            docker login ${nexus_Registry_URL}
                            # 登录失败：Error: Cannot perform an interactive login from a non TTY device  （期待解决）
                            # 发现不登录也可push，那就不登录了。

                            echo '####################################'
                            echo '#### 本地镜像${dockerImageName}:${PomVersion}.${BUILD_NUMBER} 重命名为 ${nexus_Registry_URL}/${dockerImageName}:${PomVersion}.${BUILD_NUMBER}'
                            echo '####################################'
                            docker tag ${dockerImageName}:${PomVersion}.${BUILD_NUMBER} ${nexus_Registry_URL}/${dockerImageName}:${PomVersion}.${BUILD_NUMBER}

                            echo '####################################'
                            echo '#### 推送shop镜像到Nexus3仓库'
                            echo '####################################'
                            docker push ${nexus_Registry_URL}/${dockerImageName}:${PomVersion}.${BUILD_NUMBER}
                        """
                    }
                }
            }
        }

        stage('导出DB数据') {
            steps {
                // 切换到mysql-image目录做mysql相关工作
                dir('mysql-image') {
                    echo '####################################'
                    echo '#### git拉取MySQL镜像工程的全套相关文件'
                    echo '####################################'
                    //credentialsId 是Jenkins中配置的凭证ID，用于访问GitLab
                    //变量 env.GitLabURL4MySQL ，是gitlab代码仓库的URL
                    git branch: 'main', credentialsId: 'c00cb58c-1457-4703-835d-9198ffa5cc0a', url: env.GitLabURL4MySQL

                    echo '####################################'
                    echo "#### 从开发库导出MySQL中的数据到文件"
                    echo '####################################'
                    script {
                        if( env.deploy_env=='env-cn'){
                            //导出env-cn环境的mysql数据
                            sh "mysqldump -h${cn_mysql_ip} -P${cn_mysql_port} -u${cn_mysql_user} -p${cn_mysql_pass} -B --add-drop-database --add-drop-table --default-character-set=utf8 --hex-blob --max-allowed-packet=1024M --routines --events --triggers ${cn_mysql_dbname} > shop.sql"
                        }else{
                            //导出env-en环境的mysql数据
                            sh "mysqldump -h${en_mysql_ip} -P${en_mysql_port} -u${en_mysql_user} -p${en_mysql_pass} -B --add-drop-database --add-drop-table --default-character-set=utf8 --hex-blob --max-allowed-packet=1024M --routines --events --triggers ${en_mysql_dbname} > shop.sql"
                        }
                    }

                    echo '####################################'
                    echo "#### 导入MySQL中的数据到一个临时库"
                    echo '####################################'
                    script {
                        //33号数据库就是临时库，账号信息请根据你的实际情况来修改，账号信息:IP：192.168.92.33,端口：3306,账号：root/666666
                        //导入env-cn到临时库33号数据库
                        sh "mysql -h192.168.92.33 -P3306 -uroot -p666666 -e 'source ./shop.sql'"

                    }

                    echo '####################################'
                    echo "#### 在临时库清理一些账号数据，如：支付宝、微信支付、三方网关等等账号信息"
                    echo '####################################'
                    script {
                        //临时库33号数据库信息请根据你的实际情况来修改，账号信息:IP：192.168.92.33,端口：3306,账号：root/666666
                        if( env.deploy_env=='env-cn'){
                            //执行清理SQL，清理账号信息、无用信息 (注意：数据库名称不同)
                            sh """
                                mysql -h192.168.92.33 -P3306 -uroot -p666666 <<EOF
                                use shop-cn-2;
                                #清理：支付相关账号，如：支付宝、微信支付
                                UPDATE settlement_pay_way_attr SET pay_way_value='请改为你公司的账号信息' WHERE 1=1;
                                #清理：短信通道表
                                UPDATE sys_sms_server SET access_key='请改为你公司的账号信息',app_id='请改为你公司的账号信息',client_id='请改为你公司的账号信息' WHERE 1=1;
                                #清理：快递通道表 快递鸟
                                UPDATE sys_express_server SET ebusiness_id='请改为你公司的账号信息',appkey='请改为你公司的账号信息' WHERE 1=1;
                                #邮件通道表
                                UPDATE sys_email_server SET smtp='请改为你公司的账号信息',PORT='请改为你公司的账号信息',username='请改为你公司的账号信息',pwd='请改为你公司的账号信息' WHERE 1=1;

                            """
                        }else{
                            //执行清理SQL，清理账号信息、无用信息 (注意：数据库名称不同)
                            sh """
                                mysql -h192.168.92.33 -P3306 -uroot -p666666 <<EOF
                                use shop-en-3;
                                #清理：支付相关账号，如：支付宝、微信支付
                                UPDATE settlement_pay_way_attr SET pay_way_value='请改为你公司的账号信息' WHERE 1=1;
                                #清理：短信通道表
                                UPDATE sys_sms_server SET access_key='请改为你公司的账号信息',app_id='请改为你公司的账号信息',client_id='请改为你公司的账号信息' WHERE 1=1;
                                #清理：快递通道表 快递鸟
                                UPDATE sys_express_server SET ebusiness_id='请改为你公司的账号信息',appkey='请改为你公司的账号信息' WHERE 1=1;
                                #邮件通道表
                                UPDATE sys_email_server SET smtp='请改为你公司的账号信息',PORT='请改为你公司的账号信息',username='请改为你公司的账号信息',pwd='请改为你公司的账号信息' WHERE 1=1;

                            """
                        }
                    }

                    echo '####################################'
                    echo "#### 从临时库导出MySQL中的数据到文件"
                    echo '####################################'
                    script {
                        //临时库33号数据库信息请根据你的实际情况来修改，账号信息:IP：192.168.92.33,端口：3306,账号：root/666666
                        if( env.deploy_env=='env-cn'){
                            //从33号临时库数据库再次导出,导出env-cn环境的mysql数据
                            sh "mysqldump -h192.168.92.33 -P3306 -uroot -p666666 -B --add-drop-database --add-drop-table --default-character-set=utf8 --hex-blob --max-allowed-packet=1024M --routines --events --triggers ${cn_mysql_dbname} > shop.sql"
                        }else{
                            //从33号临时库数据库再次导出,导出env-en环境的mysql数据
                            sh "mysqldump -h192.168.92.33 -P3306 -uroot -p666666 -B --add-drop-database --add-drop-table --default-character-set=utf8 --hex-blob --max-allowed-packet=1024M --routines --events --triggers ${en_mysql_dbname} > shop.sql"
                        }
                    }

                }
            }
        }
        stage('创建MySQL镜像') {
            steps {
                // 切换到mysql-image目录做mysql相关工作
                dir('mysql-image') {
                    //问：[uri: 'tcp://192.168.92.44:2375'] 是哪来的？ 答：它是一个远程的Docker引擎，连接上它对它下达命令。
                    withDockerServer([uri: 'tcp://192.168.92.44:2375', credentialsId: '']) {
                        sh """#!/bin/bash
                        echo '####################################'
                        echo "#### 当前工作目录："
                        echo '####################################'
                        pwd

                        echo '####################################'
                        echo "#### 工作目录的内容："
                        echo '####################################'
                        ls -a

                        echo '####################################'
                        echo "#### 创建MySQL的Docker镜像"
                        echo '####################################'
                        # 下面的BUILD_NUMBER变量是jenkins自身提供的变量，是构建次数。
                        docker build -t ${imageMysql}:${PomVersion}.${BUILD_NUMBER} .

                        echo '####################################'
                        echo '#### 现有的本地镜像 ${imageMysql}:${PomVersion}.${BUILD_NUMBER} 重命名为 ${nexus_Registry_URL}/${imageMysql}:${PomVersion}.${BUILD_NUMBER}'
                        echo '####################################'
                        docker tag ${imageMysql}:${PomVersion}.${BUILD_NUMBER} ${nexus_Registry_URL}/${imageMysql}:${PomVersion}.${BUILD_NUMBER}

                        echo '####################################'
                        echo '#### 推送${imageMysql}镜像到Nexus3仓库'
                        echo '####################################'
                        docker push ${nexus_Registry_URL}/${imageMysql}:${PomVersion}.${BUILD_NUMBER}
                    """
                    }
                }
            }
        }

        stage('启动一组容器') {
            steps {
                script {
                    //问：[uri: 'tcp://192.168.92.44:2375'] 是哪来的？ 答：它是一个远程的Docker引擎，连接上它对它下达命令。
                    if( env.deploy_env=='env-cn'){
                        //中文商城在192.168.92.44启动
                        env.dockerServerUrl='tcp://192.168.92.44:2375'
                    }else{
                        //英文商城在192.168.92.43启动
                        env.dockerServerUrl='tcp://192.168.92.43:2375'
                    }
                }
                withDockerServer([uri: env.dockerServerUrl ,credentialsId :'']) {
                    sh """#!/bin/bash
                        echo '####################################'
                        echo "#### 停止一批容器"
                        echo '####################################'
                        docker stop shop
                        docker stop shop-mysql
                        docker stop shop-solr-alone8
                        docker stop shop-redis

                        echo '####################################'
                        echo "#### 删除一批容器"
                        echo '####################################'
                        docker rm shop
                        docker rm shop-mysql
                        docker rm shop-solr-alone8
                        docker rm shop-redis

                        echo '####################################'
                        echo "#### 准备Docker网络"
                        echo '####################################'
                        docker network rm my-net
                        docker network create --subnet=172.28.0.0/16 my-net

                        echo '####################################'
                        echo "#### 启动一批容器"
                        echo '####################################'
                        docker run -d -p ${docker_redis_port}:6379 --name shop-redis --ip ${docker_redis_ip} --restart=always --network my-net  sichengtech/redis:6.2.10 redis-server /etc/redis/redis.conf
                        docker run -d -p ${docker_solr_port}:8983 -t --name shop-solr-alone8 --ip ${docker_solr_ip} --network my-net nexus.sicheng.net:8083/solr-alone:8.7.0
                        docker run -d -p ${docker_mysql_port}:3306 -e MYSQL_ROOT_PASSWORD=123456 --ip ${docker_mysql_ip} --network my-net --name shop-mysql nexus.sicheng.net:8083/${imageMysql}:${PomVersion}.${BUILD_NUMBER}
                    """
                    script {
                        if( env.deploy_env=='env-cn'){
                            sh """
                            echo "#### 等Msyql容器启动完成,第一次启动时会导入初始数据，需要一些时间。中文商城数据量小，等1分钟"
                            sleep 60
                            """
                        }else{
                            sh """
                            echo "#### 等Msyql容器启动完成,第一次启动时会导入初始数据，英文版数据量大，要等5分钟呢。"
                            sleep 300
                            """
                        }
                    }
                    sh """
                        docker run -d -p ${docker_shop_port}:8080 --name shop --ip ${docker_shop_ip} --network my-net  nexus.sicheng.net:8083/${dockerImageName}:${PomVersion}.${BUILD_NUMBER}
                    """
                    echo '本步结束'
                }
            }
        }
        stage("发送邮件") {
			steps{
			    //本发邮件功能依赖jinkins后台的配置，在【系统管理】➡【系统配置Configure System】➡【Extended E-mail Notification】需要提交配置了SMTP相关信息，邮件模板等信息。
                //下面代码中的变量：$DEFAULT_SUBJECT、${DEFAULT_RECIPIENTS} 等等都是上面步骤的配置中出现的，可直接取用。
				emailext(
                    from:'admin@mail.sicheng.net', //发件人邮箱
                    to:'${DEFAULT_RECIPIENTS}',  //收件人邮箱
                    mimeType:'text/html',
                    subject:'$DEFAULT_SUBJECT', //邮件主题
                    body:'${DEFAULT_CONTENT}', //邮件内容。
                    attachLog: true, //Bool类型，是否将构建日志以附件形式发送。
                    compressLog: true , //Bool类型，是否压缩日志。
                )
			}
		}

    }
}
