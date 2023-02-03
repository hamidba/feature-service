import React, {useEffect, useState} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from '../common/AppNavbar';
import {Link} from 'react-router-dom';


const FeatureList = () => {

    const [features, setFeatures] = useState([]);
    const [loading, setLoading] = useState(false);


    useEffect(() => {
        setLoading(true);

        fetch('api/features', {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
        })
            .then(response => response.json())
            .then(data => {
                setFeatures(data);
                setLoading(false);
            })
    }, []);

    if (loading) {
        return <p>Loading...</p>;
    }

    const remove = async (id) => {
        await fetch(`/api/features/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        }).then(() => {
            let updatedFeatures = [...features].filter(i => i.id !== id);
            setFeatures(updatedFeatures);
        });
    }

    const featureList = features.map(feature => {
        return <tr key={feature.id}>
                    <td>{feature.displayName}</td>
                    <td>{feature.technicalName}</td>
                    <td>{feature.expiresOn}</td>
                    <td>{feature.description}</td>
                    <td>{feature.inverted === true ? 'Yes' : 'No'}</td>
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"/features/" + feature.id}>Edit</Button>
                            <Button size="sm" color="danger" onClick={() => remove(feature.id)}>Delete</Button>
                        </ButtonGroup>
                    </td>
                </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-end">
                    <Button color="success" tag={Link} to="/features/new">Create Feature</Button>
                </div>
                <h3>Feature List</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="20%">Display Name</th>
                        <th width="20%">Technical Name</th>
                        <th width="20%">Expires On</th>
                        <th width="20%">Description</th>
                        <th width="20%">Inverted</th>
                        <th width="20%">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                        {featureList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
}

export default FeatureList;
