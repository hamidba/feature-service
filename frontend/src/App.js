import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import FeatureList from "./components/feature/FeatureList";
import FeatureEdit from "./components/feature/FeatureEdit";

function App() {
    return (
        <Router>
            <Routes>
                <Route exact path="/" element={<FeatureList/>}/>
                <Route
                    path="/features"
                    exact={true}
                    element={<FeatureList/>}
                />
                <Route
                    path="/features/:id"
                    element={<FeatureEdit/>}
                />

            </Routes>
        </Router>
    )
}

export default App;
