import React, { Component } from 'react';
import { TabContent, TabPane, Nav, NavItem, NavLink, Badge, Input } from 'reactstrap';
import Highlight from 'react-highlight'
import classnames from 'classnames';

class Api extends Component {
  constructor(props) {
    super(props);

    this.toggle = this.toggle.bind(this);
    this.state = {
      activeTab: '1'
    };
  }

  toggle(tab) {
    if (this.state.activeTab !== tab) {
      this.setState({
        activeTab: tab
      });
    }
  }

  render() {
    return (
      <div className="App-api">
        <h2 style={{fontSize: '2.5vh', color: '#007bff'}}>API endpoints:</h2>
        <Nav tabs style={{fontSize: '2vh', width: '58.75vh'}}>
          <NavItem style={{width: '19vh'}}>
            <NavLink
              className={classnames({ active: this.state.activeTab === '1' })}
              onClick={() => { this.toggle('1'); }}
            >
              shorten <Badge color="danger">POST</Badge>
            </NavLink>
          </NavItem>
          <NavItem style={{width: '19vh'}}>
            <NavLink
              className={classnames({ active: this.state.activeTab === '2' })}
              onClick={() => { this.toggle('2'); }}
            >
              get <Badge color="success">GET</Badge>
            </NavLink>
          </NavItem>
          <NavItem style={{width: '19vh'}}>
            <NavLink
              className={classnames({ active: this.state.activeTab === '3' })}
              onClick={() => { this.toggle('3'); }}
            >
              count <Badge color="success">GET</Badge>
            </NavLink>
          </NavItem>
        </Nav>
        <TabContent activeTab={this.state.activeTab} style={{width: '58.75vh'}}>
          <TabPane tabId="1">
            <div style={{marginTop: '1vh'}}>
              <h5 style={{fontSize: '2vh', height: '2.5vh', margin: '0 auto', backgroundColor: '#007bff', color: 'white'}}>
                https://{window.location.hostname}/api/shorten
              </h5>
              <div style={{fontSize: '2vh', float: 'left', marginLeft: '1vh'}}>
                <p style={{marginTop: '0.5vh', lineHeight: '2.25vh', float: 'left'}}>
                  Receives valid link and optional custom id:
                </p><br/>
                <div style={{marginTop: '-1.5vh', float: 'left', marginLeft: '-1vh'}}>
                  <Highlight>
                    {'{"url": "https://delfi.lv", "customId": "custom"}'}
                  </Highlight>
                </div>
              </div>
              <div style={{fontSize: '2vh', float: 'left', marginLeft: '1vh'}}>
                <p style={{marginTop: '0.5vh', lineHeight: '2.25vh', float: 'left'}}>
                  Responses with:
                </p><br/>
                <div style={{marginTop: '-1.5vh', float: 'left', marginLeft: '-1vh'}}>
                  <Highlight>
                    {'{"shortUrl": "http://localhost/r/baTo"}'}
                  </Highlight>
                </div>
              </div>
              {/* <Input
                style={{fontSize: '2vh', height: '2.5vh', width: '22vh', margin: '0 auto'}}
                readOnly
                placeholder={'{"urlCount": "241098"}'}
              /> */}
            </div>
          </TabPane>
          <TabPane tabId="2">
            <div style={{marginTop: '1vh'}}>
              <h5 style={{fontSize: '2vh', height: '2.5vh', margin: '0 auto', backgroundColor: '#007bff', color: 'white'}}>
                https://{window.location.hostname}/api/get/{'{id}'}
              </h5>
              <p style={{fontSize: '2vh', marginTop: '0.5vh', lineHeight: '2.25vh'}}>
                Returns long url from the given id. <br/>
                Responses with:
              </p>
              <div style={{fontSize: '2vh'}}>
                <Highlight>
                  {'{"longUrl": "http://github.com"}'}
                </Highlight>
              </div>
            </div>
          </TabPane>
          <TabPane tabId="3">
            <div style={{marginTop: '1vh'}}>
              <h5 style={{fontSize: '2vh', height: '2.5vh', margin: '0 auto', backgroundColor: '#007bff', color: 'white'}}>
                https://{window.location.hostname}/api/count
              </h5>
              <p style={{fontSize: '2vh', marginTop: '0.5vh', lineHeight: '2.25vh'}}>
                Returns count of shortened links. <br/>
                Responses with:
              </p>
              <div style={{fontSize: '2vh'}}>
                <Highlight>
                  {'{"urlCount": "241098"}'}
                </Highlight>
              </div>
            </div>
          </TabPane>
        </TabContent>
      </div>
    );
  }
}

export default Api;