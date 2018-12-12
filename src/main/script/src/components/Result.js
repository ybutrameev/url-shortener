import React from 'react';
import {Button, Input, Form, FormGroup, InputGroup} from 'reactstrap';
import FontAwesome from 'react-fontawesome'

const Result = props => (
	<Form action={props.shortUrl} target="_blank">
	  <FormGroup className="Main-form-group">
	    {
		  	props.shortUrl && 
		  	<>
					<InputGroup>
						<Input
							readOnly
							id="Input-result"
							placeholder={props.shortUrl} />
						<Button className="Button" id="Button-result" color="primary">
							<FontAwesome name='arrow-right' />
						</Button>
					</InputGroup>
				</>
	  	}
			{
				props.errorMessage &&
				<div className="Error-message">
					{props.errorMessage}
				</div>
			}
	  </FormGroup>
	</Form>
);

export default Result;