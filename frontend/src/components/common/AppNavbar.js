import React from 'react';
import {Navbar, NavbarBrand} from 'reactstrap';
import {Link} from 'react-router-dom';

const AppNavbar = () => {

    return (
        <Navbar color="dark" dark expand="md">
            <NavbarBrand tag={Link} to="/features">Feature Service</NavbarBrand>
        </Navbar>
    );
};

export default AppNavbar;
