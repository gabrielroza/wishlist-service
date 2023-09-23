rootProject.name = "modular-wishlist-service"


include("domain", "spring-app", "use-case", "spring-controller", "mongo-repository")
project(":domain").projectDir = File("./core/domain")
project(":use-case").projectDir = File("./core/use-case")
project(":spring-app").projectDir = File("./app/spring-app")
project(":spring-controller").projectDir = File("./adapter/input/spring-controller")
project(":mongo-repository").projectDir = File("./adapter/output/mongo-repository")
