import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from "react-router-dom";
import AppNavbar from "../common/AppNavbar";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";

const FeatureEdit = () => {

    const emptyState = {
        id: '',
        displayName: '',
        technicalName: '',
        expiresOn: '',
        description: '',
        inverted: false
    };

    const [feature, setFeature] = useState(emptyState);
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect(() => {
        if (id !== 'new') {
            fetch(`/api/features/${id}`, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
            })
            .then(response => response.json())
            .then(data => setFeature(data));
        }
    }, [id, setFeature]);

    const handleChange = (event) => {
        const { name, value } = event.target
        setFeature({ ...feature, [name]: value })
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        await fetch('/api/features' + (feature.id ? '/' + feature.id : ''), {
            method: (feature.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(feature)
        }).then(() => setFeature(emptyState))
        .then(() => navigate('/features'))
        .catch(e => console.log(e));
    }


    return (<div>
            <AppNavbar/>
            <Container>
                <h2>{feature.id ? 'Edit Feature' : 'Add Feature'}</h2>
                <Form onSubmit={handleSubmit}>
                    <FormGroup>
                        <Label for="name">Display Name</Label>
                        <Input type="text" name="displayName" id="displayName" value={feature.displayName || ''}
                               onChange={handleChange} autoComplete="displayName"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="name">Technical Name</Label>
                        <Input type="text" name="technicalName" id="technicalName" value={feature.technicalName || ''}
                               onChange={handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="name">Expires On</Label>
                        <Input type="date" name="expiresOn" id="expiresOn" value={feature.expiresOn || ''}
                               onChange={handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="description">Description</Label>
                        <Input type="text" name="description" id="description" value={feature.description || ''}
                               onChange={handleChange} autoComplete="description"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="inverted">Inverted</Label>
                        <Input type="checkbox" name="inverted" id="inverted" value={feature.inverted} checked={feature.inverted}
                               onChange={handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/features">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    );
}

export default FeatureEdit;
