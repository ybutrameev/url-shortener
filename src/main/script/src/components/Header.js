import React from 'react';
import Odometer from 'react-odometerjs';
import '../odometer-theme-default.css';

const Header = props => (
    <header className="App-header">
      <h1 className="Counter-count">
        <Odometer value={props.urlCount} format="(,ddd)" />
      </h1>
      <p className="Counter-text">
        enormous links were shortened so far.<br/>
        Let's short another one!
      </p>
    </header>
);

export default Header;