# selenide_tests
Selenide_tests - Page Object Model | Herokuapp - interactions with different objects and elements, Logback


Setup Selenoid from scretch

https://www.youtube.com/watch?v=mxHqCMhpnaE&t=7s
https://aerokube.com/selenoid/latest/#_quick_start_guide

    // Run Selenoid + see live browser screen                  ./cm selenoid start --vnc --force
    // Stop Selenoid                                           ./cm selenoid stop

    // Run Selenoid-UI                                         ./cm selenoid-ui start --force
    // Stop Selenoid-ui                                        ./cm selenoid-ui stop

    // Open web page for displaying selenoid-UI                 http://localhost:8080

    // Status                                                  ./cm selenoid status
    // Help with all commands                                  ./cm selenoid --help


    // Lists running containers      docker ps -a
    // List of images                docker images

    // Stop container with ID        docker stop 'my_container ID'
    // Remove container with ID      docker rm 'my_container ID'

    // Help                          docker --help

    // check port   lsof -i:4444

    /*
    docker stop $(docker ps -a -q);
    docker rm $(docker ps -a -q);
    docker volume rm $(docker volume ls -qf dangling=true)
    docker network rm $(docker network ls -q)
    sudo lsof -nP | grep LISTEN
    sudo kill -9 1548
     */

    // STEP INSTRUCTIONS:
    /*
    1. Run docker
    2. Run Selenoid VNC - ./cm selenoid start --vnc --force
    3. Run Selenoid UI  - ./cm selenoid-ui start --force
    4. Open browser  -    http://localhost:8080
    5. Make sure both indicators are green
    6. Add Selenoid conficurations to test (@Before) - see the code below in @BeforeSuite
    7. Run tests
    8. After work:
    9. Display lists with running containers  -  docker ps -a
    10. Stop containers (both Selenoid and Selenoid-ui containres) -  docker stop 'my_container ID'
    11. Remove both containers -  docker rm 'my_container ID'
    12. Make sure no containers any more  -  docker ps -a
    13. Stop Selenoid-ui  -  ./cm selenoid-ui stop
    14. Stop Selenoid -  ./cm selenoid stop
    15. Clean Selenoid configuration -  ./cm selenoid cleanup
    16. Close docker
     */

    @BeforeSuite
    public void setup() throws MalformedURLException {

        /*
        // Selenoid conficurations
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("enableVNC", true);
        Configuration.browserCapabilities = capabilities;
        Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.browser = "chrome";
        Configuration.browserSize = "1280x1024";
        */
