import React, { Component } from 'react';
import Forms from "../components/Forms";
import Result from "../components/Result";

class Main extends Component {
  render() {
    return (
      <div className="App-main">
        <Forms shortenUrl = {this.props.shortenUrl} />
        <Result 
          shortUrl = {this.props.shortUrlObject.shortUrl}
          errorMessage = {this.props.shortUrlObject.message}
        />
      </div>
    );
  }
}

export default Main;