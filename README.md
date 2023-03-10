# Feature Service

This application is a simple service for managing Features toggle.

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

[Node.js]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

Note that the frontend code is located under the "frontend" directory.

```
npm install
```

We use npm scripts as our build system.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

```
./mvnw
cd frontend && npm start
```

Then navigate to [http://localhost:3000](http://localhost:3000) in your browser.

To run the project using maven only, run the following command (This will package both backend and frontend as a single executable) :

```
./mvnw
```

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.



