package io.zeebe.script;

import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class EvaluationPythonTest {

    private final ScriptEvaluator scriptEvaluator = new ScriptEvaluator();

    @Test
    public void shouldReturnNumber() {

        final Object result =
                scriptEvaluator.evaluate("python", "x * 2", Collections.singletonMap("x", 2));

        assertThat(result).isEqualTo(4);
    }

    @Test
    public void shouldReturnString() {

        final Object result =
                scriptEvaluator.evaluate(
                        "python", "'url?id={}'.format(id)", Collections.singletonMap("id", 123));

        assertThat(result).isEqualTo("url?id=123");
    }

    @Test
    public void shouldReturnInlineObject() {

        @SuppressWarnings("unchecked")
        final Map<String, Object> result =
                (Map<String, Object>)
                        scriptEvaluator.evaluate(
                                "python",
                                "{'foo':foo,'bar':'bar'}",
                                Collections.singletonMap("foo", 123));

        assertThat(result).hasSize(2).contains(entry("bar", "bar"), entry("foo", 123));
    }
}
