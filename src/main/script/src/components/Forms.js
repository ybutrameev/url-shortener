import React from 'react';
import {Button, Input, Form, FormGroup, InputGroup, InputGroupAddon} from 'reactstrap';

const Forms = props => (
	<Form onSubmit={props.shortenUrl}>
    <FormGroup className="Main-form-group">
      <InputGroup>
        <InputGroupAddon id="Main-input-group-addon" addonType="prepend">URL:</InputGroupAddon>
        <Input 
          id="Input-long-url" 
          type="text" 
          name="longUrl"
        />
        <Button className="Button" id="Button-main" color="primary">Shorten</Button>
      </InputGroup>
    </FormGroup>
    <FormGroup className="Custom-form-group">
      <InputGroup>
        <InputGroupAddon id="Custom-input-group-addon" addonType="prepend">Custom ID:</InputGroupAddon>
        <Input 
          id="Input-custom-id"
          type="text" 
          name="customId" 
          placeholder="optional (up to 12 symbols)"/>
      </InputGroup>
    </FormGroup>
  </Form>
);

export default Forms;