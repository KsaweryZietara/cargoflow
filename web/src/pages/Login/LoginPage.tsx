import { Button, ButtonGroup, Content, Form, Heading, InlineAlert, TextField } from "@adobe/react-spectrum";
import { Flex } from "@react-spectrum/layout";
import { useContext, useState } from "react";
import { AuthorizationContext } from "../../components/Authorization/Authorization";
import { useLocation, useNavigate } from "react-router-dom";

export default function LoginPage() {
    const { login } = useContext(AuthorizationContext);
    const [error, setError] = useState("");

    const navigate = useNavigate();
    const location = useLocation();
    const from = location.state?.from?.pathname || "/dashboard";

    const onFormSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = e.currentTarget;
        const username = form.username.value;
        const password = form.password.value;

        login(username, password)
            .then(() => {
                navigate(from, { replace: true });
            })
            .catch((error) => {
                setError(`Server returned: ${error}`);
            });
    };

    return (
        <Flex height={"100%"} justifyContent="center" alignItems="center" direction="column">
            {error && (
                <InlineAlert variant="negative" maxWidth="size-4600">
                    <Heading>Error</Heading>
                    <Content>{error}</Content>
                </InlineAlert>
            )}
            <Form autoComplete="off" onSubmit={onFormSubmit} maxWidth="size-4600" validationBehavior="native">
                <TextField label="Username" name="username" isRequired />
                <TextField name="password" label="Password" type="password" isRequired />
                <ButtonGroup marginTop={"single-line-height"}>
                    <Button
                        onPress={() => {
                            setError("");
                        }}
                        type="submit"
                        variant="accent"
                        width={"100%"}
                    >
                        Submit
                    </Button>
                </ButtonGroup>
            </Form>
        </Flex>
    );
}
