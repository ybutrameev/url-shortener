import React, { Component } from 'react';
import './App.css';
import {
  Form, Input,
} from 'reactstrap';

class App extends Component {
  state = {
    counterObject: -1,
    shortUrlObject: '',
    longUrl: '',
    customId: ''
  };

  async componentDidMount() {
    this.getUrlCount();
    setInterval(this.getUrlCount, 3000);
  }

  getUrlCount = () => {
    (async () => {
      const counterResponse = await fetch('http://localhost:8080/api/count');
      const counterBody = await counterResponse.json();

      this.setState({ counterObject: counterBody });
    })();
  };

  handleChange = (event) => {
    this.setState({ [event.target.name] : event.target.value });
  }

  handleSubmit = (event) => {
    event.preventDefault();
    var longUrl = this.state.longUrl;
    var customId = this.state.customId;
    var json = {};

    if(!longUrl) {
      return;
    } else {
      longUrl = longUrl.trim();
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

      const shortenBody = await shortenResponse.json();
      this.setState({ shortUrlObject: shortenBody });

      this.getUrlCount();
    })();
  }

  render() {
    const {counterObject, shortUrlObject} = this.state;

    return (
      <div className="App">
        <header className="App-header">
          <div className="App-intro">
            {counterObject.urlCount} URLs were shortened so far.<br/>
            Let's short another one!
            <Form onSubmit={this.handleSubmit}>
              <Input
                type="text"
                name="longUrl"
                placeholder="url"
                onChange={this.handleChange} />
              <Input
                type="text"
                name="customId"
                placeholder="customId"
                onChange={this.handleChange} />
              <Input type="submit" value="Shorten" />
            </Form>
            <a href={shortUrlObject.shortUrl} target="_blank" rel="noopener noreferrer">{shortUrlObject.shortUrl}</a>
          </div>
        </header>
      </div>
    );
  }
}

export default App;