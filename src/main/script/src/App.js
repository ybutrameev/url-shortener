import React, { Component } from 'react';
import Header from "./components/Header";
import Main from './components/Main';
import Api from "./components/Api";
import Footer from "./components/Footer";
import './App.css';

class App extends Component {
  state = {
    counterObject: {},
    shortUrlObject: {},
    longUrl: undefined,
    customId: undefined
  };

  async componentDidMount() {
    this.setUrlCount();
    setInterval(this.setUrlCount, 3000);
  }

  setUrlCount = () => {
    (async () => {
      const counterResponse = await fetch('http://localhost:8080/api/count');
      const counterObject = await counterResponse.json();

      this.setState({ counterObject });
    })();
  };

  shortenUrl = (event) => {
    event.preventDefault();
    var longUrl = event.target.elements.longUrl.value;
    var customId = event.target.elements.customId.value;
    var json = {};

    if(longUrl) {
      longUrl = longUrl.trim();
    } else {
      return;
    }

    if(customId) {
      customId = customId.trim();
      json = {"url": `${longUrl}`, "customId": `${customId}`};
    } else {
      json = {"url": `${longUrl}`, "customId": null};
    }

    (async () => {
      const shortenResponse = await fetch('http://localhost:8080/api/shorten', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(json)
      });

      const shortUrlObject = await shortenResponse.json();
      this.setState({ shortUrlObject });

    })();
  }

  render() {
    return (
      <div className="App">
        <Header urlCount = {this.state.counterObject.urlCount} />
        <Main 
          shortenUrl = {this.shortenUrl}
          shortUrlObject = {this.state.shortUrlObject}
        />
        <Api />
        <Footer/>
      </div>
    );
  }
}

export default App;